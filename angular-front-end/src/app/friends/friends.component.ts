import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataServiceService } from '../data-service.service';

@Component({
  selector: 'app-friends',
  templateUrl: './friends.component.html',
  styleUrls: ['./friends.component.scss']
})
export class FriendsComponent implements OnInit {

  private dataService:DataServiceService;
  activatedRoute: ActivatedRoute;
  userData;
  userName;
  totalExpense;
  userInfo;
  loggedIn=0;
  map= new Map();

  constructor(dataService:DataServiceService,activatedRoute: ActivatedRoute) {
    this.activatedRoute=activatedRoute; 
    this.dataService=dataService;
  }

  ngOnInit(): void {
    this.loggedIn=1;
    this.userData=this.dataService.getUserDetails();
    this.dataService.getUserDataChanged.subscribe( () => {
      this.userData=this.dataService.userData;
      console.log(this.userData);
    }
    );
    
    this.activatedRoute.params.subscribe(
      (params) => {
        this.userName=params.userName;
      },
    );
    
    this.dataService.friendsInfoData(this.userName);
    this.dataService.getFriendsInfoChanged.subscribe( () => {
      this.userInfo=this.dataService.userInfo;
      for(let i in this.userInfo) {
        let k=this.userInfo[i].indexOf(":");
        let key=this.userInfo[i].substring(0,k+1);
        key=key.substring(1);
        key=key.substring(-1);
        key=key.substring(0,key.length-1);
        let value=this.userInfo[i].substring(k+1);
        value=value.substring(0,value.length-1);
        this.map.set(key,value);
        console.log("map="+this.map);

      }
    })

    this.totalExpense=this.dataService.getExpenseData(this.userName);
    this.dataService.getExpenseChanged.subscribe( () => {
       this.totalExpense=this.dataService.getExpenseData(this.userName);
       this.totalExpense=this.totalExpense.substring(18);
       this.totalExpense=Number(this.totalExpense);

    })
 
  }


}
