import{u as I,y as j,f as r,z as p,o as c,c as m,b as o,w as d,v as b,A as w}from"./index-8efb2234.js";import{g as A,r as k,m as M}from"./board-a0137818.js";const B=["onSubmit"],N={class:"mb-3"},S=o("label",{for:"userid",class:"form-label"},"작성자 ID : ",-1),U=["disabled"],V={class:"mb-3"},D=o("label",{for:"subject",class:"form-label"},"제목 : ",-1),E={class:"mb-3"},R=o("label",{for:"content",class:"form-label"},"내용 : ",-1),T={class:"col-auto text-center"},z={key:0,type:"submit",class:"btn btn-outline-primary mb-3"},C={key:1,type:"submit",class:"btn btn-outline-success mb-3"},q={__name:"BoardFormItem",props:{type:String},setup(v){const f=v,_=I(),g=j(),u=r(!1),t=r({articleNo:0,subject:"",content:"",userId:"",userName:"",hit:0,registerTime:""});if(f.type==="modify"){let{articleno:s}=g.params;A(s,({data:e})=>{t.value=e,u.value=!0},e=>{console.error(e)}),u.value=!0}const a=r(""),n=r("");p(()=>t.value.subject,s=>{let e=s.length;e==0||e>30?a.value="제목을 확인해 주세요!!!":a.value=""},{immediate:!0}),p(()=>t.value.content,s=>{let e=s.length;e==0||e>500?n.value="내용을 확인해 주세요!!!":n.value=""},{immediate:!0});function y(){a.value?alert(a.value):n.value?alert(n.value):f.type==="regist"?h():x()}function h(){console.log("글등록하자!!",t.value),k(t.value,s=>{let e="글등록 처리시 문제 발생했습니다.";s.status==201&&(e="글등록이 완료되었습니다."),alert(e),i()},s=>console.error(s))}function x(){console.log(t.value.articleNo+"번글 수정하자!!",t.value),M(t.value,s=>{let e="글수정 처리시 문제 발생했습니다.";s.status==200&&(e="글정보 수정이 완료되었습니다."),alert(e),i()},s=>console.log(s))}function i(){_.push({name:"article-list"})}return(s,e)=>(c(),m("form",{onSubmit:w(y,["prevent"])},[o("div",N,[S,d(o("input",{type:"text",class:"form-control","onUpdate:modelValue":e[0]||(e[0]=l=>t.value.userId=l),disabled:u.value,placeholder:"작성자ID..."},null,8,U),[[b,t.value.userId]])]),o("div",V,[D,d(o("input",{type:"text",class:"form-control","onUpdate:modelValue":e[1]||(e[1]=l=>t.value.subject=l),placeholder:"제목..."},null,512),[[b,t.value.subject]])]),o("div",E,[R,d(o("textarea",{class:"form-control","onUpdate:modelValue":e[2]||(e[2]=l=>t.value.content=l),rows:"10"},null,512),[[b,t.value.content]])]),o("div",T,[v.type==="regist"?(c(),m("button",z," 글작성 ")):(c(),m("button",C,"글수정")),o("button",{type:"button",class:"btn btn-outline-danger mb-3 ms-1",onClick:i}," 목록으로이동... ")])],40,B))}};export{q as _};
