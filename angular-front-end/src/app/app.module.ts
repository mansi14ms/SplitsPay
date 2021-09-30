import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { SignUpComponent } from './sign-up/sign-up.component';
import { AddExpenseComponent } from './add-expense/add-expense.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HttpClientModule } from '@angular/common/http';
import { CreateGroupComponent } from './create-group/create-group.component';
import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { MultiSelectAllModule } from '@syncfusion/ej2-angular-dropdowns';
import { DashboardComponent } from './dashboard/dashboard.component';
import { FriendsComponent } from './friends/friends.component';
import { GroupsComponent } from './groups/groups.component';
import * as Rx from "rxjs/Rx";
import { GroupDetailsComponent } from './group-details/group-details.component';

const routes = [
  { path: '', component: LoginComponent },
  { path: 'signup', component: SignUpComponent },
  { path: 'dashboard/groups/:userName/groupInfo/:groupId/add-expense/:groupId', component: AddExpenseComponent },
  { path: 'create-group', component: CreateGroupComponent },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'dashboard/friends', component: FriendsComponent },
  { path: 'dashboard/groups', component: GroupsComponent },
  { path: 'dashboard/friends/:userName', component: FriendsComponent },
  { path: 'dashboard/groups/:userName', component: GroupsComponent },
  { path: 'dashboard/groups/:userName/groupInfo/:groupId', component: GroupDetailsComponent},

  
];

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    SignUpComponent,
    AddExpenseComponent,
    CreateGroupComponent,
    DashboardComponent,
    FriendsComponent,
    GroupsComponent,
    GroupDetailsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    RouterModule.forRoot(routes),
    BrowserAnimationsModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MultiSelectAllModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
