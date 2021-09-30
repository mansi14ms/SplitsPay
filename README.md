## SPLITPAY APPLICATION

#### APIs

`POST ("/signup")`
>{
    "username" : String,
	"fullName" :  String,
	"email" :  String,
	"phone :  String,
	"password" : String
}

`POST ("/login")`
>{
    "username" : String,
	"password" : String,
}

`POST ("/group")`
>{
    "groupName" : String,
	"description" :  String,
	"members" :  List[String]
}

`POST ("/transaction")`
>{
    "billFor" : String,
	"fromUser" :  String,
	  "toUser" :  List[String],
	  "paidBy" :  String,
	  "date" : String,.
	  "amount" : float
}


###### To get  all the user details
`GET ("/users")`

###### To get  all the groups by userName
`GET ("/group/{userName}")`

###### To get  all the group users by groupId
`GET ("/group/users/{groupId}")`

###### To get  group total
`GET ("/amount/total/{userName}")`

###### To get  transactions by groupId
`GET ("/transactions/{groupId}")`

###### To get  all friends by userName
`GET ("/friends/{userName}")`

###### To get  all balances
`GET ("/balances")`






