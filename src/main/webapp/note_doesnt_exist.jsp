<%@ include file="header.jsp" %>
<html>
<head>
  <title>Note 1 </title>
  <link rel="stylesheet" href="styles.css">
</head>
<body>
<%@ include file="sidebar.jsp" %>
<div id="content">
  <div class = "note">
    <h1>This note doesnt exist!</h1>
    <h1>The ID you tried to enter was: ${param.id}</h1>

  </div>
</div>
</body>
</html>
