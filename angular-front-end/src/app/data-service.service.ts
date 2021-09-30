import { Injectable } from '@angular/core';
import { HttpSeviceService } from './http-sevice.service';
import { UserDetails } from './sign-up/sign-up.component';
import { LoginDetails } from './login/login.component';
import { Subject } from 'rxjs-compat';
import { NewGroupDetails } from './create-group/create-group.component';
import { NewExpense } from './add-expense/add-expense.component';
import { GroupDetailsComponent } from './group-details/group-details.component';

@Injectable({
  providedIn: 'root'
})
export class DataServiceService {

  groupDetailsComponent : GroupDetailsComponent;
  private httpService : HttpSeviceService;
  getUserDataChanged = new Subject<void>();
  getLoginDataChanged = new Subject<void>();
  getGroupDataChanged =new Subject<void>();
  getFriendsChanges = new Subject<void>();
  getExpenseChanged = new Subject<void>();
  getBillsDataChanged = new Subject<void>();
  getFriendsInfoChanged = new Subject<void>();
  getSignUpDataChanged = new Subject<void>();
  errorStatus;
  userData;
  randomValue;
  groupData;
  friendsData = [];
  friendsList;
  totalExpense;
  billsData;
  userInfo;
  signUpMessage;
  loginMessage;

  constructor(httpService: HttpSeviceService) {
    this.httpService = httpService;
  }

  signUpNewUserData(userDetails: UserDetails) {
    return this.httpService.signUpNewUser(userDetails).subscribe(
      (response: string) => {
        console.log("response=");
        console.log(response);
        this.signUpMessage = response;
        this.getSignUpDataChanged.next();
      },
      (error) => {
        console.log("response=");
        console.log(error);
        this.signUpMessage = error.status;
        this.getSignUpDataChanged.next();
      }
    );
  }

  loginUserData(loginDetails: any) {
    this.errorStatus = null;
    return this.httpService.loginUser(loginDetails).subscribe(
      (response: string) => {
        console.log("response:");
        console.log(response);
        this.loginMessage=response;
        this.getLoginDataChanged.next();
      },
      (error) => {
        console.log("response=");

        console.log(error);
        console.log(error.status);
        this.errorStatus = error.status;
        this.getLoginDataChanged.next();
      }
    );
  }

  getUserDetails() {
    return this.httpService.getUserData().subscribe((response: any) => {
      this.userData = response;
      this.getUserDataChanged.next();
    });
  }

  createGroupData(newGroupDetails: NewGroupDetails) {
    //return this.httpService.createGroup(newGroupDetails);
    this.randomValue = Math.floor(100000 + Math.random() * 90);
    newGroupDetails.groupId =
      newGroupDetails.groupName + String(this.randomValue);
    console.log(newGroupDetails.groupId);
    return this.httpService.createGroup(newGroupDetails);
  }

  addExpensesInfo(newExpense: NewExpense, groupId: String, c) {
    console.log("Add expense");
    console.log(groupId);
    newExpense.groupId = groupId;
    newExpense.amount = newExpense.amount / c;
    return this.httpService.addExpense(newExpense);
  }

  getGroupData(userName: String) {
    return this.httpService
      .getGroupInfo(userName)
      .subscribe((response: any) => {
        this.groupData = response;
        this.getGroupDataChanged.next();
      });
  }

  getFriendsData(groupId) {
    this.httpService.getFriends(groupId);
    this.httpService.getFriends(groupId).subscribe((response: any) => {
      console.log("This is response : ");
      console.log(response);
      this.friendsData.push(response);
      this.getFriendsChanges.next();
    });
    return this.friendsList;
  }

  getExpenseData(userName: String) {
    this.httpService.getExpense(userName).subscribe(
      (response: any) => {
        this.getExpenseChanged.next();
      },
      (error) => {
        this.totalExpense = error.error.text;
        this.getExpenseChanged.next();
      }
    );
    return this.totalExpense;
  }

  getOutingBillsData(groupId) {
    this.httpService.getOutingBills(groupId).subscribe((response: any) => {
      this.billsData = response;
      this.getBillsDataChanged.next();
    });
  }

  friendsInfoData(userName) {
    this.httpService.friendsInfo(userName).subscribe((response: any) => {
      response = response.substring(1);
      response = response.substring(0, response.length - 1);
      let splitted = response.split(",");
      this.userInfo = splitted;
      this.getFriendsInfoChanged.next();
    });
  }

  getGroupBills() {
    this.groupDetailsComponent.getGroupAmount();
  }
}
