<div class="n-head">
    <div class="g-doc f-cb">
        <div class="user">
        <#if user>
            <#if user.usertype==1>卖家<#else>买家</#if>你好，<span class="name">${user.username}</span>！<a href="${base}/logout">[退出]</a>
        <#else>
           	 请<a href="${base}/login">[登录]</a>
        </#if>
        </div>
        <ul class="nav">
            <li><a href="${base}/">首页</a></li>
            <#if user  && user.usertype==0>
            <li><a href="${base}/account">账务</a></li>
            <li><a href="${base}/settleAccount">购物车</a></li>
            </#if>
            <#if user && user.usertype==1>
            <li><a href="${base}/public">发布</a></li>
            </#if>
        </ul>
    </div>
</div>