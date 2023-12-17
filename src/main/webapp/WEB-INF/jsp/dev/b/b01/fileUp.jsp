<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8">
  <title>Insert title here</title>
</head>
<body>
<h1>file upload</h1>
<form method="post" action="/file/write" enctype="multipart/form-data">
  <input type="file" name="uploadfile" multiple="multiple">
  <input type="submit">
  <div class="form-group" align="left">
    <label for="subject">파일:</label>
    <input type="file" class="form-control-file border" name="upfile" multiple="multiple">
  </div>
</form>
</body>
</html>