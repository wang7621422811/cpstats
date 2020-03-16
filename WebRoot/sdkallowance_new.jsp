<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>SDK限量</title>
    <link href="css/main.css" type="text/css" rel="stylesheet" />
    <script src="js/ajax.js" type=text/javascript></script>
    <script src="js/Calendar.js" type="text/javascript"></script>

    <style>
        .inputtext {
            margin: 1px 0 0 0;
            border: 1px solid #dedede;
        }
    </style>
</head>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link href="css/main.css" type="text/css" rel="stylesheet" />
    <script src="js/ajax.js" type=text/javascript></script>
    <script src="js/Calendar.js" type="text/javascript"></script>
    <title>SDK余量</title>
    <style>
        .inputtext {
            margin: 1px 0 0 0;
            border: 1px solid #dedede;
        }
    </style>
</head>
<body>
<form name="form1" method="post" action="#" id="form1" onsubmit="selectform();">

    <table>
        <tr>
            <td>&nbsp;&nbsp;移动SDK余量查询-->结算时间范围:
                <input name="starttime" type="text" value="<c:out value="${date}"></c:out>"
                       id="txtBegTime" onclick="new Calendar().show(this);"
                       style="width: 80px; margin: 1px 0 0 0; border: 1px solid #dedede;" />
                到
                <input name="endtime" type="text" value="<c:out value="${date}"></c:out>"
                       id="txtEndTime" onclick="new Calendar().show(this);"
                       style="width: 80px; margin: 1px 0 0 0; border: 1px solid #dedede;" />
                &nbsp;&nbsp;
                <input type="submit" name="btnAdd" value="查询数据"
                       id="btnAdd"
                       style="BORDER-RIGHT: #7b9ebd 1px solid; PADDING-RIGHT: 2px; BORDER-TOP: #7b9ebd 1px solid; PADDING-LEFT: 2px; FONT-SIZE: 12px; FILTER: progid : DXImageTransform.Microsoft.Gradient ( GradientType = 0, StartColorStr = #ffffff, EndColorStr = #cecfde ); BORDER-LEFT: #7b9ebd 1px solid; CURSOR: hand; COLOR: black; PADDING-TOP: 2px; BORDER-BOTTOM: #7b9ebd 1px solid;" />
            </td>
            <td style="font-size: 24px;color:red;">&nbsp;&nbsp;<b>注：100%表示有余量，0%表示没有余量，“--”表示暂停</b></td>
        </tr>
    </table>

    <hr />

    <table align=center class=table1 id="tab">
        <tr>
            <td>省份</td>
            <td>1元&nbsp;|&nbsp;金币10</td>
            <td>2元&nbsp;|&nbsp;金币80</td>
            <td>2元&nbsp;|&nbsp;金币200</td>
            <td>2元&nbsp;|&nbsp;运气金币</td>
            <td>3元&nbsp;|&nbsp;金币100</td>
            <td>5元&nbsp;|&nbsp;金币300</td>
        </tr>
        <c:forEach items="${provinceLimits}" var="provinceLimit">
            <tr>
                <td><c:out value="${provinceLimit.province}"></c:out></td>
                <!-- type==1==壳子限量 -->
                <c:if test="${provinceLimit.type==1}">
                    <td>${provinceLimit.one}%</td>
                    <c:choose>
                        <c:when test="${provinceLimit.two == -1}">
                            <td> -- </td>
                        </c:when>
                        <c:otherwise>
                            <%--如果当前余量小于0则更新成0--%>
                            <c:choose>
                                <c:when test="${provinceLimit.one<=0}">
                                    <td>0%</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${provinceLimit.one}%</td>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${provinceLimit.twoO == -1}">
                            <td> -- </td>
                        </c:when>
                        <c:otherwise>
                            <td>${provinceLimit.one}%</td>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${provinceLimit.twoT == -1}">
                            <td> -- </td>
                        </c:when>
                        <c:otherwise>
                            <td>${provinceLimit.one}%</td>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${provinceLimit.three == -1}">
                            <td> -- </td>
                        </c:when>
                        <c:otherwise>
                            <td>${provinceLimit.one}%</td>
                        </c:otherwise>
                    </c:choose>

                    <c:choose>
                        <c:when test="${provinceLimit.five == -1}">
                            <td> -- </td>
                        </c:when>
                        <c:otherwise>
                            <td>${provinceLimit.one}%</td>
                        </c:otherwise>
                    </c:choose>
                </c:if>

                <!-- type==2==分资费限量 -->
                <c:if test="${provinceLimit.type==2}">
                    <%--2元 1--%>
                    <c:choose>
                        <c:when test="${provinceLimit.one == -1}">
                            <td> -- </td>
                        </c:when>
                        <c:otherwise>
                            <%--如果当前余量小于0则更新成0--%>
                            <c:choose>
                                <c:when test="${provinceLimit.one<=0}">
                                    <td>0%</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${provinceLimit.one}%</td>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    <%--2元 1--%>
                    <c:choose>
                        <c:when test="${provinceLimit.two == -1}">
                            <td> -- </td>
                        </c:when>
                        <c:otherwise>
                            <%--如果当前余量小于0则更新成0--%>
                            <c:choose>
                                <c:when test="${provinceLimit.two<=0}">
                                    <td>0%</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${provinceLimit.two}%</td>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    <%--2元 2--%>
                    <c:choose>
                        <c:when test="${provinceLimit.twoO == -1}">
                            <td> -- </td>
                        </c:when>
                        <c:otherwise>
                            <%--如果当前余量小于0则更新成0--%>
                            <c:choose>
                                <c:when test="${provinceLimit.twoO<=0}">
                                    <td>0%</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${provinceLimit.twoO}%</td>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    <%--2元 3--%>
                    <c:choose>
                        <c:when test="${provinceLimit.twoT == -1}">
                            <td> -- </td>
                        </c:when>
                        <c:otherwise>
                            <%--如果当前余量小于0则更新成0--%>
                            <c:choose>
                                <c:when test="${provinceLimit.twoT<=0}">
                                    <td>0%</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${provinceLimit.twoT}%</td>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>

                    <%--3元--%>
                    <c:choose>
                        <c:when test="${provinceLimit.three == -1}">
                            <td> -- </td>
                        </c:when>
                        <c:otherwise>
                            <%--如果当前余量小于0则更新成0--%>
                            <c:choose>
                                <c:when test="${provinceLimit.three<=0}">
                                    <td>0%</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${provinceLimit.three}%</td>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    <%--5元--%>
                    <c:choose>
                        <c:when test="${provinceLimit.five == -1}">
                            <td> -- </td>
                        </c:when>
                        <c:otherwise>
                            <%--如果当前余量小于0则更新成0--%>
                            <c:choose>
                                <c:when test="${provinceLimit.five<=0}">
                                    <td>0%</td>
                                </c:when>
                                <c:otherwise>
                                    <td>${provinceLimit.five}%</td>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                </c:if>
            </tr>
        </c:forEach>
        <td>新疆</td>
        <td>100%</td>
        <td>100%</td>
        <td>100%</td>
        <td>100%</td>
        <td>100%</td>
        <td>100%</td>
    </table>
    <div align="center">
    </div>
</form>
</body>
</html>
