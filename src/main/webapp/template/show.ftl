<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<#include "/include/support.ftl">
<#include "/include/header.ftl">
<div class="g-doc">
    <#if !product>
    <div class="n-result">
        <h3>内容不存在！</h3>
    </div>
    <#else>
    <div class="n-show f-cb" id="showContent">
        <div class="img"><img src="${product.image}" alt="" ></div>
        <div class="cnt">
            <h2>${product.title}</h2>
            <p class="summary">${product.summary}</p>
            <div class="price">
                <span class="v-unit">¥</span><span class="v-value">${product.price}</span>
            </div>
            <div class="num">购买数量：<span id="plusNum" class="lessNum"><a>-</a></span><span class="totalNum" id="allNum">1</span><span id="addNum" class="moreNum"><a>+</a></span></div>
            <div class="num">已售出：<span class="totalNum">${product.saleNum}</span> 件</div>
            <div class="oprt f-cb">
            	<#if user && user.usertype==0>
                    <button class="u-btn u-btn-primary" id="add" data-id="${product.id}" data-title="${product.title}" data-price="${product.price?c}">
                                                  加入购物车</button>
                    <#if product.isBuy>
                    <!--<span class="u-btn u-btn-primary z-dis">已购买</span>-->
                    <span class="buyprice">您购买次数：${product.times}；最近一次购买价格：¥${product.buyPrice};总购买数：${product.buyNum};</span>
                    </#if>
                <#elseif user && user.usertype==1>
                </#if>
            </div>
            <div class="oprt f-cb">
                
                <#if user && user.usertype==1>
                <a href="${base}/edit?id=${product.id}" class="u-btn u-btn-primary">编 辑</a>
                </#if>
            </div>
        </div>
    </div>
    <div class="m-tab m-tab-fw m-tab-simple f-cb">
        <h2>详细信息</h2>
    </div>
    <div class="n-detail">
        ${product.detail}
    </div>
    </#if>
</div>
<#include "/include/footer.ftl">
<script type="text/javascript" src="${base}/js/global.js"></script>
<script type="text/javascript" src="${base}/js/pageShow.js"></script>
</body>
</html>