<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<#include "/include/support.ftl">
<#include "/include/header.ftl">
<#if RequestParameters['type']??>
<#assign listType = RequestParameters['type']?number >
</#if>
<div class="g-doc">
    <div class="m-tab m-tab-fw m-tab-simple f-cb">
        <div class="tab">
            <ul>
                <li <#if !listType?? || listType != 1>class="z-sel"</#if> ><a href="${base}/">所有内容</a></li>
                <#if user && user.usertype == 0><li <#if listType == 1>class="z-sel"</#if> ><a href="${base}/?type=1">未购买的内容</a></li></#if>
            </ul>
        </div>
    </div>
    <#if !productList || !productList?has_content>
    <div class="n-result">
        <h3>暂无内容！</h3>
    </div>
    <#else>
    <div class="n-plist">
        <ul class="f-cb" id="plist">
        <#if user && user.usertype == 0 && listType == 1>
            <#list productList as x>
                <#if !x.isBuy>
                <li id="p-${x.id}">
                    <a href="${base}/show?id=${x.id}" class="link">
                        <div class="img"><img src="${x.image}" alt="${x.title}"></div>
                        <h3>${x.title}</h3>
                        <div class="price"><span class="v-unit">¥</span><span class="v-value">${x.price}</span></div>
                    </a>
                </li>
                </#if>
            </#list>
        <#else>
            <#list productList as x>
                <li id="p-${x.id}">
                    <a href="${base}/show?id=${x.id}" class="link">
                        <div class="img"><img src="${x.image}" alt="${x.title}"></div>
                        <h3>${x.title}</h3>
                        <div class="price"><span class="v-unit">¥</span><span class="v-value">${x.price}</span></div>
                        <#if user && user.usertype==0 && x.isBuy ><span class="had"><b>已购买</b></span></#if>
                        <#if user && user.usertype==1 && x.isSell ><span class="had"><b>已售出</b></span></#if>
                    </a>
                    <#if user && user.usertype==1 && !x.isSell><span class="u-btn u-btn-normal u-btn-xs del" data-del="${x.id}">删除</span></#if>
                </li>
            </#list>
        </#if>
        </ul>
    </div>
    <#if !RequestParameters['type']??>
    <div id="pager"></div>
    <!--分页 -->
	<script type="text/javascript" src="${base}/scripts/jQuery1.11.3/jquery-1.11.3.min.js" ></script>
	<link href="${base}/scripts/pagination22/pagination.css"  type="text/css" rel="stylesheet" />
	<script type="text/javascript" src="${base}/scripts/pagination22/jquery.pagination2.2.js" ></script>
	<script type="text/javascript">
	$(document).ready(function() {
		//初始化分页组件
		var count=${count};
		var size=${size};
		var pageNO=${pageNO};
		$("#pager").pagination(count, {
			items_per_page:size,
			current_page:pageNO-1,
			next_text:"下一页",
			prev_text:"上一页",
			num_edge_entries:2,
			load_first_page:false,
			link_to:"${base}",
			callback:handlePaginationClick
		});
	
		//回调方法
		function handlePaginationClick(new_page_index, pagination_container){
			location.href="${base}/?pageNO="+(new_page_index+1);
			return false;
		}
		
		var defaultSrc="${base}/images/default.jpg";
			$(".tab img").bind("error",function(){
			$(this).prop("src",defaultSrc);
		});
	});
	</script>
	</#if>
    </#if>
</div>
<#include "/include/footer.ftl">
<script type="text/javascript" src="${base}/js/global.js"></script>
<script type="text/javascript" src="${base}/js/pageIndex.js"></script>
</body>
</html>