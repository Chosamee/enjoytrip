import{_ as E,r as x,o as _,c as g,b as t,t as d,a as P,j as y,k as T,l as k,F as C,m as b,n as V,u as $,f as v,p as N,q as A,w as L,v as R,x as S}from"./index-890abee0.js";import{l as w}from"./board-f1109c63.js";const O={class:"text-center"},B={scope:"row"},j={class:"text-start"},K={__name:"BoardListItem",props:{article:Object},setup(s){return(m,c)=>{const o=x("router-link");return _(),g("tr",O,[t("th",B,d(s.article.articleNo),1),t("td",j,[P(o,{to:{name:"article-view",params:{articleno:s.article.articleNo}},class:"article-title link-dark"},{default:y(()=>[T(d(s.article.subject),1)]),_:1},8,["to"])]),t("td",null,d(s.article.userName),1),t("td",null,d(s.article.hit),1),t("td",null,d(s.article.registerTime),1)])}}},D=E(K,[["__scopeId","data-v-9ae55a19"]]);const U={class:"row"},M={class:"pagination justify-content-center"},G={class:"page-item"},Z={class:"page-item"},z=["onClick"],F={class:"page-item"},Y={class:"page-item"},q={__name:"VPageNavigation",props:{currentPage:Number,totalPage:Number},emits:["pageChange"],setup(s,{emit:m}){const c=s,o=parseInt("10"),r=k(()=>parseInt((c.currentPage-1)/o)*o+1),p=k(()=>{let n=parseInt((c.currentPage-1)/o)*o+o;return c.totalPage<n?c.totalPage:n}),I=k(()=>parseInt((c.totalPage-1)/o)*o<c.currentPage);function i(n,l){const a=[];for(let e=n;e<=l;e++)a.push(e);return a}function u(n){console.log(n+"로 이동!!!"),m("pageChange",n)}return(n,l)=>(_(),g("div",U,[t("ul",M,[t("li",G,[t("a",{class:"page-link",onClick:l[0]||(l[0]=a=>u(1))},"최신")]),t("li",Z,[t("a",{class:"page-link",onClick:l[1]||(l[1]=a=>u(r.value==1?1:r.value-1))},"이전")]),(_(!0),g(C,null,b(i(r.value,p.value),a=>(_(),g("li",{key:a,class:V(s.currentPage===a?"page-item active":"page-item")},[t("a",{class:"page-link",onClick:e=>u(a)},d(a),9,z)],2))),128)),t("li",F,[t("a",{class:"page-link",onClick:l[2]||(l[2]=a=>u(I.value?s.totalPage:p.value+1))},"다음")]),t("li",Y,[t("a",{class:"page-link",onClick:l[3]||(l[3]=a=>u(s.totalPage))},"마지막")])])]))}},H=E(q,[["__scopeId","data-v-933dfd93"]]),W={class:"container"},J={class:"row justify-content-center"},Q=t("div",{class:"col-lg-10"},[t("h2",{class:"my-3 py-3 shadow-sm bg-light text-center"},[t("mark",{class:"sky"},"글목록")])],-1),X={class:"col-lg-10"},tt={class:"row align-self-center mb-2"},et={class:"col-md-5 offset-5"},at={class:"d-flex"},st={class:"input-group input-group-sm ms-1"},ot={class:"table table-hover"},nt=t("thead",null,[t("tr",{class:"text-center"},[t("th",{scope:"col"},"글번호"),t("th",{scope:"col"},"제목"),t("th",{scope:"col"},"작성자"),t("th",{scope:"col"},"조회수"),t("th",{scope:"col"},"작성일")])],-1),rt={__name:"BoardList",setup(s){const m=$(),c=v([{text:"검색조건",value:""},{text:"글번호",value:"article_no"},{text:"제목",value:"subject"},{text:"작성자아이디",value:"user_id"}]),o=v([]),r=v(1),p=v(0),{VITE_ARTICLE_LIST_SIZE:I}={VITE_OPEN_API_SERVICE_KEY:"공공데이터키",VITE_KAKAO_MAP_SERVICE_KEY:"a4135a62e96a2f955116bac201057818",VITE_VUE_API_URL:"http://localhost:8080/api",VITE_ELECTRIC_CHARGING_STATION_URL:"https://apis.data.go.kr/B552584/EvCharger/getChargerInfo",VITE_ARTICLE_LIST_SIZE:"20",VITE_ARTICLE_NAVIGATION_SIZE:"10",BASE_URL:"/",MODE:"production",DEV:!1,PROD:!0,SSR:!1},i=v({pgno:r.value,spp:I,key:"",word:""});N(()=>{n()});const u=e=>{i.value.key=e},n=()=>{w(i.value,({data:e})=>{o.value=e.articles,r.value=e.currentPage,p.value=e.totalPageCount},e=>{console.error(e)})},l=e=>{r.value=e,i.value.pgno=e,n()},a=()=>{m.push({name:"article-write"})};return(e,f)=>(_(),g("div",W,[t("div",J,[Q,t("div",X,[t("div",tt,[t("div",{class:"col-md-2 text-start"},[t("button",{type:"button",class:"btn btn-outline-primary btn-sm",onClick:a}," 글쓰기 ")]),t("div",et,[t("form",at,[P(A,{selectOption:c.value,onOnKeySelect:u},null,8,["selectOption"]),t("div",st,[L(t("input",{type:"text",class:"form-control","onUpdate:modelValue":f[0]||(f[0]=h=>i.value.word=h),placeholder:"검색어..."},null,512),[[R,i.value.word]]),t("button",{class:"btn btn-dark",type:"button",onClick:n},"검색")])])])]),t("table",ot,[nt,t("tbody",null,[(_(!0),g(C,null,b(o.value,h=>(_(),S(D,{key:h.articleNo,article:h},null,8,["article"]))),128))])])]),P(H,{"current-page":r.value,"total-page":p.value,onPageChange:l},null,8,["current-page","total-page"])])]))}};export{rt as default};