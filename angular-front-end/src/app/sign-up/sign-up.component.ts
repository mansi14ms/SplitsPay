import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { FormGroup, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { DataServiceService } from '../data-service.service';

export interface UserDetails {
  userName: String;
  fullName: String;
  phone: String;
  email: String;
  password: String;
}

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.scss']
})
export class SignUpComponent implements OnInit {

 
  private dataService:DataServiceService;
  private router:Router;
  status:any;

  profileForm = new FormGroup({
    userName: new FormControl('', [Validators.required,Validators.maxLength(350)]),
    password: new FormControl('',[Validators.required,Validators.maxLength(8),Validators.minLength(4)]),
    fullName: new FormControl('', [Validators.required,]),
    phone: new FormControl('', [Validators.required,Validators.maxLength(10),Validators.minLength(10)]),
    email: new FormControl('', [Validators.required,Validators.maxLength(35)])

  })

  constructor(dataService: DataServiceService, router: Router) {
    this.dataService = dataService;
    this.router = router;
  }

  ngOnInit(): void {}

  submitForm() {
    console.log("sending");
    if (!this.profileForm.valid) 
    alert("Please provide the details for signUp");
    else {
      this.dataService.signUpNewUserData(this.profileForm.value);
      this.dataService.getSignUpDataChanged.subscribe(() => {
        this.status = this.dataService.signUpMessage;
        if (this.status == "User added successfully") {-
          console.log("successful");
          alert("User Added Successfully...Head to Login");
          this.router.navigate(["/"]);
          this.profileForm.reset();
        } 
        else if (this.status =='406 NOT_ACCEPTABLE "Username is not acceptable"') {
          console.log("error");
          alert("UserName already exists");
          this.profileForm.get('userName').reset();
        }
        else if (this.status == '406 NOT_ACCEPTABLE "Email is not acceptable"') {
          alert("Email Already Exists!!");
          this.profileForm.get('email').reset();
        }
      });
    }
  }
}
