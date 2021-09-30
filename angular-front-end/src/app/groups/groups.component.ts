import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataServiceService } from '../data-service.service';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.component.html',
  styleUrls: ['./groups.component.scss']
})
export class GroupsComponent implements OnInit {

  activatedRoute: ActivatedRoute;
  dataService: DataServiceService;
  userName;
  groupInfo;
  avatar;
  friendsData = [];
  

  constructor( activatedRoute: ActivatedRoute, dataService: DataServiceService) { 
    this.activatedRoute=activatedRoute;
    this.dataService=dataService;
    this.avatar='/assets/images/user_1.svg';
  }

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(
      (params) => {
        console.log("params="+params.userName);
        this.userName=params.userName;
      },
    );

    this.groupInfo=this.dataService.getGroupData(this.userName);
    this.dataService.getGroupDataChanged.subscribe( () => {
      this.groupInfo=this.dataService.groupData;
      let i;
      for(let i in this.groupInfo)
      this.dataService.getFriendsData(this.groupInfo[i].groupDetails.groupId);
    }
    )

    this.dataService.getFriendsChanges.subscribe(() => {
      this.friendsData = this.dataService.friendsData;
     
     
    })

    this.dataService.getGroupBills();
    
}

onClick()
{
  console.log("click");
}

}
