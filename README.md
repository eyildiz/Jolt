# Jolt
Asynchronous networking for JSON, XML and HTML webservices, websites and RSS feeds.

- No dependency on other libraries. <b>Jolt</b> is ready to go.
- Easy and quick connection to <b>WebServices, RSS Feeds</b> and <b>Websites</b>.
- <b>JSON, XML</b> and <b>HTML</b> services are supported.


<h3> How To Implement </h3>

- <b>For Android Studio</b><br>
  + Download <a href="https://github.com/emre1512/Jolt/blob/master/app/release/Jolt-1.1.jar?raw=true"> Jolt.jar </a> and add it to your projects <b>/libs</b> folder.
  + Go to app.gradle file and type <code>compile files ('libs/Jolt-1.1.jar')</code> in <code>dependenices</code> tag.

- <b>For Eclipse</b><br>
  + Download <a href="https://github.com/emre1512/Jolt/blob/master/app/release/Jolt-1.1.jar?raw=true"> Jolt.jar </a> and add it to your projects <b>/libs</b> folder.


<h3> Basic Usages </h3>
<h5> JSON Usage </h5>

```java
Jolt.connect("Your URL").
responseType(ResponseType.JSON).
registerListener(new JoltConnectionListener() { 
  @Override
    public void onSuccess(String result) {
      // do whatever you want
  }
});
```
<br>
<h5> XML Usage </h5>
```java
Jolt.connect("Your URL").
responseType(ResponseType.XML).
registerListener(new JoltConnectionListener() { 
  @Override
    public void onSuccess(String result) {
      // do whatever you want
  }
});
```
<br>
<h5> HTML Usage </h5>
```java
Jolt.connect("Your URL").
responseType(ResponseType.HTML).
registerListener(new JoltConnectionListener() { 
  @Override
    public void onSuccess(String result) {
      // do whatever you want
  }
});
```
<br>
<h3> Alternative Usages </h3>
```java
Jolt.connect("www.google.com").
responseType(ResponseType.HTML).
registerListener(new JoltConnectionListener() {

  @Override
    public void onStart() {
      // Do stuff before connection
    }
        
  @Override
    public void onSuccess(String result) {
      // Do stuff on success
    }

  @Override
    public void onFail() {
      // Do stuff on fail 
    }
 
  @Override
    public void onFinish() {
      // Do stuff after connection
    }
            
});
```

<b><h5> For questions and suggestions : emre151291@gmail.com</h5></b>
