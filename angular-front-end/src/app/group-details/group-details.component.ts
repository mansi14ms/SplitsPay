import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { DataServiceService } from "../data-service.service";

@Component({
  selector: "app-group-details",
  templateUrl: "./group-details.component.html",
  styleUrls: ["./group-details.component.scss"],
})
export class GroupDetailsComponent implements OnInit {
  activatedRoute: ActivatedRoute;
  dataService: DataServiceService;
  map = new Map<String, Map<String, number>>();
  groupId: String;
  billData;
  totalBalance = 0;
  cache = [
    {
      forBill: "none",
      paidBy: "none",
      paidFor: "none",
      amount: 0,
    },
  ];

  constructor(activatedRoute: ActivatedRoute, dataService: DataServiceService) {
    this.activatedRoute = activatedRoute;
    this.dataService = dataService;
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe((params) => {
      console.log("params=" + params.groupId);
      this.groupId = params.groupId;
    });

    this.dataService.getOutingBillsData(this.groupId);
    this.dataService.getBillsDataChanged.subscribe(() => {
      this.billData = this.dataService.billsData;

      for (let i in this.billData) {
        let amount: number = this.billData[i].amount;
        let forBill: string = this.billData[i].billFor;
        let paidBy: string = this.billData[i].fromUserDetails.userName;
        let paidFor: string = this.billData[i].toUserDetails.userName;

        let detail = {
          forBill: forBill,
          paidBy: paidBy,
          paidFor: paidFor,
          amount: amount,
        };
        this.cache.push(detail);
       
       
        if (paidBy != paidFor) {
          this.totalBalance =this.totalBalance+amount;
          if (this.map.has(forBill)) {
            let tempMap = new Map<String, number>();
            tempMap = this.map.get(forBill);
            if (tempMap.has(paidBy)) {
              let val = tempMap.get(paidBy) + amount;
              let cur_amount = this.checkOppositeValue(forBill,paidBy,paidFor);
              console.log("cur_amount=" + cur_amount);
              val = val - cur_amount;
              this.totalBalance = this.totalBalance -cur_amount;
              console.log("tot="+this.totalBalance);
              tempMap.set(paidBy, val);
              this.map.set(forBill, tempMap);
            } else {
              tempMap.set(paidBy, amount);
              this.map.set(forBill, tempMap);
            }
          } else {
            let tempMap = new Map<String, number>();
            tempMap.set(paidBy, amount);
            this.map.set(forBill, tempMap);
          }
        }
      }
    });
  }

  getGroupAmount() {
    let amountMap = new Map();

    this.map.forEach((value: Map<String, Number>, key: string) => {
      let sum = 0;
      let tempMap = new Map();
      tempMap = value;
      tempMap.forEach((value: number, key: String) => {
        sum = sum + value;
      });
      amountMap.set(key, sum);
    });

    return amountMap;
  }

  checkOppositeValue(forBill, paidBy, paidFor) {
    let sum = 0;
    for (var index = 0; index < this.cache.length; index++) {
      if (this.cache[index].forBill.localeCompare(forBill) == 0) {
 
        if (this.cache[index].paidFor.localeCompare(paidFor) == 0) {
  
          if (this.cache[index].paidBy.localeCompare(paidBy) == 0) {
      
            sum = sum + this.cache[index].amount;
           
          }
        }
      }
    }
    return sum;
  }
}
