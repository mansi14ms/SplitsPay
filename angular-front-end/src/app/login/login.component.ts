import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { DataServiceService } from '../data-service.service';

export interface LoginDetails {
  userName: String;
  password: String;
}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  private dataService:DataServiceService;
  private router:Router;
  private errorStatus;
  private userInfoName;
  private URL;
  

  profileForm = new FormGroup({
    userName: new FormControl('', [Validators.required,Validators.maxLength(350)]),
    password: new FormControl('',[Validators.required,Validators.maxLength(8),Validators.minLength(4)])
  })

  constructor(dataService:DataServiceService, router:Router) {
    this.dataService = dataService;
    this.router = router;
   }

  ngOnInit(): void {
  }
  
  submitForm() {
    this.errorStatus = null;
    let userNameNotFound :String = '404 NOT_FOUND "Username is not found"';
    let passwordWrong :String = '401 UNAUTHORIZED "Incorrect password"';
    this.userInfoName=this.profileForm.value.userName;
    if(!this.profileForm.valid)
    alert("Please enter username and password");
    else {
    this.dataService.loginUserData(this.profileForm.value);
    this.dataService.getLoginDataChanged.subscribe( () => {
      console.log("resp="+this.dataService.loginMessage);
      console.log("nop="+userNameNotFound);
      if(this.dataService.loginMessage === "Successful login!")
      {
        this.URL='/dashboard/friends/'+this.userInfoName;
        this.router.navigate([this.URL]);

      }
      else if (this.dataService.loginMessage === userNameNotFound) {
      alert("Username not found");
      }
      else if (this.dataService.loginMessage === passwordWrong) {
      alert("Wrong Password");
      this.profileForm.get('password').reset();
      }
      else{
       alert("Wrong username or password");    
    }
    });
  }
  }

}
