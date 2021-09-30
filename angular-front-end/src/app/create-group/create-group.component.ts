import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ViewEncapsulation } from '@angular/core';
import { MultiSelectComponent } from '@syncfusion/ej2-angular-dropdowns';
import { DataServiceService } from '../data-service.service';
import { Router } from '@angular/router';

export interface NewGroupDetails {
  groupId:String;
  groupName: String;
  description: String;
  members: [];
 
}

@Component({
  selector: 'app-create-group',
  templateUrl: './create-group.component.html',
  styleUrls: ['./create-group.component.scss']
})
export class CreateGroupComponent implements OnInit {

userData;
dataService:DataServiceService;
private router:Router;
public fields: Object = { text: 'userName', value: 'userName' };

public default : string = 'Default';

  createForm = new FormGroup({
    groupName: new FormControl('', [Validators.required,Validators.maxLength(80)]),
    description: new FormControl('', [Validators.required,Validators.maxLength(150)]),
    members: new FormControl([]),
  })

  constructor(dataService:DataServiceService,router:Router) {
    this.router=router;
    this.dataService=dataService;
   }

  ngOnInit(): void {
    this.userData=this.dataService.getUserDetails();
    this.dataService.getUserDataChanged.subscribe( () => {
      this.userData=this.dataService.userData;
    }
    )
  }

  submitForm() {

    if(!this.createForm.valid)
    alert("Please fill all the details")
    else
    {
    console.log(this.createForm.value.members);
    this.dataService.createGroupData(this.createForm.value);
    alert("group created");
    }
}

  reset() {
    this.createForm.reset();
  }

}
