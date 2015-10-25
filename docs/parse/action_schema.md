# Action Object

The action object represents something that the campaign would like a supporter to do. Things like calling somebody, sending an email, tweet / retweet, post to facebook, etc.

```
let actionObject = PFObject(className: "Action")
```

### List View

The action objects should be queried and ordered by priority descending, then by createdAt descending.

```
query.addDescendingOrder("priority")
query.addDescendingOrder("createdAt")
```

### Action Types ###

| Action Type | Description |
| ----------- | ----------- |
| general | A general call to action with no specific integrations. | 
| url | A general call to action that will open the browser to the specified url. This can be used for directing people to fill out a petition, comment on a blog post / forum, etc. |
| call | Place a phone call to the specified person. |
| email | Send an email to the specified person / people. |
| tweet | Send a tweet to your followers, the provided text should pre-populate the tweet if possible. | 
| retweet | Retweet a specified tweet. |
| tweet_reply | Reply to a tweet, the provided text should pre-populate the tweet if possible. The reply should also reference the included tweet id. |
| fb_post | Post to Facebook, the provided text should pre-populate the post if possible. | 
| fb_share | Share a provided url to Facebook, the provided text should pre-populate the share if possible. |
| attend | Physical presence is needed at some event, etc. The request should advise people of a common hashtag to use while attending the event. |
| avatar | Set social media avatar. |
| instagram | Post to Instagram. |

### Object Attributes / Columns

| Attribute | Data Type | Required | Description | 
| --------- | --------- | -------- | ----------- |
| objectId | string | (auto) | Automatically generated primary key. | 
| actionType | ActionType | Y | The type of action that is being called for. This drives the other fields which should be required or not based on the type. | 
| title | string | N | The title of the requested action |
| message | string | Y | The text describing the action that needs to be taken. |
| thumbnailUrl | url | N | The thumbnail image that is shown in the list view for the actions. If not specified, this should default to an in-app default image. |
| imageUrl | url | N | The image that is shown in the action detail view. If this is not specified, it should not show an image on the detail screen.|
| createdAt | datetime | (auto) | The time the record was created. |
| updatedAt | datetime | (auto) | The time the recond was last updated. |
| priority | number | Y | Should default to 0. Field is used to control the order of the action list. The higher the priority, the higher on the list the item is.|


### Actions with additional fields

Most actions have additional fields that are needed to fulfil the integrations, etc.

#### Visit a Webpage: `url`

| Attribute | Data Type | Required | Description | 
| --------- | --------- | -------- | ----------- |
| ref | url | Y | The url to open up in the user's web-browser. (Not in-app webview) |

#### Make a Phone Call: `call`

| Attribute | Data Type | Required | Description | 
| --------- | --------- | -------- | ----------- |
| ref | string | Y | The phone number (only digits, no dashes, periods, etc) this should prompt the user to dial, but not call the number without the user clicking the actual call button. (i.e. Android Dial intent) | 

#### Send an Email: `email`

| Attribute | Data Type | Required | Description | 
| --------- | --------- | -------- | ----------- |
| subject | string | Y | The text that should pre-populate the subject line of the email. | 
| body | string | Y | The text that should pre-populate the email body. |
| recipients | Array<string> | Y | An array of email addresses that should pre-populate in the To: field of the email. |

#### Send a Tweet: `tweet`

| Attribute | Data Type | Required | Description | 
| --------- | --------- | -------- | ----------- |
| body | string | Y | The text that should pre-populate as the tweet. | 

#### Retweet a Message: `retweet`

| Attribute | Data Type | Required | Description | 
| --------- | --------- | -------- | ----------- |
| ref | string | Y | The id of the tweet that needs to be retweeted. | 

#### Reply to a Tweet: `tweet_reply`

| Attribute | Data Type | Required | Description | 
| --------- | --------- | -------- | ----------- |
| body | string | Y | The text that should pre-populate the text of the reply. |
| ref | string | Y | The id of the tweet that should be replied to. | 

#### Post to Facebook: `fb_post`

| Attribute | Data Type | Required | Description | 
| --------- | --------- | -------- | ----------- |
| body | string | Y | The text that should pre-populate the Facebook post. | 

#### Share a Link on Facebook: `fb_share`

| Attribute | Data Type | Required | Description | 
| --------- | --------- | -------- | ----------- |
| ref | string | Y | The url that needs to be shared via Facebook. | 
| body | string | Y | The text that should pre-populate the Facebook share post. |

#### Update your Social Media Avatars: `avatar`

| Attribute | Data Type | Required | Description | 
| --------- | --------- | -------- | ----------- |
| ref | string | Y | The url or base64 encoded image to use in-place of the user's avatar on social networks. | 

#### Post to Instagram: `instagram`

| Attribute | Data Type | Required | Description | 
| --------- | --------- | -------- | ----------- |
| ref | string | Y | The url or base64 encoded image to post to Instagram. | 

