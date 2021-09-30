import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ViewEncapsulation } from '@angular/core';
import { MultiSelectComponent } from '@syncfusion/ej2-angular-dropdowns';
import { DataServiceService } from '../data-service.service';
import { ActivatedRoute } from '@angular/router';

export interface NewExpense {
  groupId:String;
  billFor: String;
  amount;
  date : String;
  fromUser: String;
  toUser: [];
  id:String;
}



@Component({
  selector: 'app-add-expense',
  templateUrl: './add-expense.component.html',
  styleUrls: ['./add-expense.component.scss']
})
export class AddExpenseComponent implements OnInit {

  dataService:DataServiceService;
  activeRoute:ActivatedRoute;
  userData;
  groupId;

public fields: Object = { 
  text: 'userName', 
   value: 'userName' 
  };

public default : string = 'Default';

    profileForm = new FormGroup({
    billFor: new FormControl('', [Validators.required,Validators.maxLength(350)]),
    amount: new FormControl('', [Validators.required,Validators.maxLength(350)]),
    date: new FormControl('',[Validators.required]),
    fromUser: new FormControl('', [Validators.required]),
    toUser: new FormControl('', [Validators.required,Validators.maxLength(35)])

  })

  constructor(dataService:DataServiceService, activeRoute:ActivatedRoute) {
    this.dataService=dataService;
    this.activeRoute=activeRoute;
   }

  ngOnInit(): void {

    this.activeRoute.params.subscribe(
      (params) => {
        console.log("params="+params.groupId);
        this.groupId=params.groupId;
      },
    );

    this.userData=this.dataService.getUserDetails();
    this.dataService.getUserDataChanged.subscribe( () => {
      this.userData=this.dataService.userData;
      console.log("create-group data");
      console.log(this.userData);
    }
    )
  }

  submitForm() {
    if(!this.profileForm.valid)
    alert("Please fill all the details")
    else {
    let size=this.profileForm.value.toUser;
    let c=0;
    for(let i in size)
    c++;
    this.dataService.addExpensesInfo(this.profileForm.value,this.groupId,c+1) ;
    alert("Expense Added Successfully");

     }
  }

  reset() {
    this.profileForm.reset();
  }

}
