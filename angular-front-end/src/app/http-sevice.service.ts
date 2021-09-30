import { Injectable } from '@angular/core';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { UserDetails } from './sign-up/sign-up.component';
import { LoginDetails } from './login/login.component';
import { NewGroupDetails } from './create-group/create-group.component';
import { NewExpense } from './add-expense/add-expense.component';

@Injectable({
  providedIn: 'root'
})
export class HttpSeviceService {

  readonly BASE_URL;
  private httpClient:HttpClient;
  private errorStatus;
  
  constructor(httpClient: HttpClient) {
    this.httpClient = httpClient;
    this.BASE_URL = "http://localhost:8080/";
  }

  signUpNewUser(userDetails: UserDetails) {
    return this.httpClient.post(this.BASE_URL + "signup", userDetails, {
      responseType: "text",
    });
  }

  loginUser(loginDetails: any) {
    return this.httpClient.post(this.BASE_URL + "login", loginDetails, {
      responseType: "text" as "json",
    });
  }

  getUserData() {
    return this.httpClient.get(this.BASE_URL + "users");
  }

  createGroup(newGroupDetails: NewGroupDetails) {
    this.errorStatus = undefined;
    this.httpClient
      .post(this.BASE_URL + "group", newGroupDetails, {
        responseType: "text" as "json",
      })
      .subscribe(
        (response: string) => {
          console.log("response:");
          console.log(response);
        },
        (error) => {
          console.log(error.status);
          this.errorStatus = error.status;
        }
      );
    return this.errorStatus;
  }

  addExpense(newExpense: NewExpense) {
    return this.httpClient
      .post(this.BASE_URL + "transactions", newExpense, {
        responseType: "text" as "json",
      })
      .subscribe((response: string) => {
        console.log("response:");
        console.log(response);
      });
  }

  getGroupInfo(userName: String) {
    return this.httpClient.get(this.BASE_URL + "group/" + userName);
  }

  getFriends(groupId: String) {
    return this.httpClient.get(this.BASE_URL + "group/users/" + groupId);
  }

  getExpense(userName: String) {
    return this.httpClient.get(this.BASE_URL + "amount/total/" + userName);
  }

  getOutingBills(groupId) {
    return this.httpClient.get(this.BASE_URL + "transactions/" + groupId);
  }

  friendsInfo(userName) {
    return this.httpClient.get(this.BASE_URL + "friends/" + userName, {
      responseType: "text" as "json",
    });
  }
}
