import{u as _,d as f,s as h,e as g,f as v,o as y,c as k,b as s,w as r,v as i,g as w}from"./index-7fdafe8d.js";const x={class:"container"},S={class:"row justify-content-center"},U=s("div",{class:"col-lg-10"},[s("h2",{class:"my-3 py-3 shadow-sm bg-light text-center"},[s("mark",{class:"orange"},"로그인")])],-1),M={class:"col-lg-10"},B=s("div",{class:"form-check mb-3 float-end"},[s("input",{class:"form-check-input",type:"checkbox"}),s("label",{class:"form-check-label",for:"saveid"}," 아이디저장 ")],-1),K={class:"mb-3 text-start"},L=s("label",{for:"userid",class:"form-label"},"아이디 : ",-1),T={class:"mb-3 text-start"},V=s("label",{for:"userpwd",class:"form-label"},"비밀번호 : ",-1),C=["onKeyup"],j={__name:"UserLogin",setup(I){const a=_(),n=f(),{isLogin:u}=h(n),{userLogin:d,getUserInfo:m}=n,{changeMenuState:p}=g(),e=v({email:"",password:""}),l=async()=>{await d(e.value);let c=sessionStorage.getItem("accessToken");u&&(m(c),p()),a.push({name:"main"})},b=()=>{a.push("/regist")};return(c,t)=>(y(),k("div",x,[s("div",S,[U,s("div",M,[s("form",null,[B,s("div",K,[L,r(s("input",{type:"text",class:"form-control","onUpdate:modelValue":t[0]||(t[0]=o=>e.value.email=o),placeholder:"아이디..."},null,512),[[i,e.value.email]])]),s("div",T,[V,r(s("input",{type:"password",class:"form-control","onUpdate:modelValue":t[1]||(t[1]=o=>e.value.password=o),onKeyup:w(l,["enter"]),placeholder:"비밀번호..."},null,40,C),[[i,e.value.password]])]),s("div",{class:"col-auto text-center"},[s("button",{type:"button",class:"btn btn-outline-primary mb-3",onClick:l}," 로그인 "),s("button",{type:"button",class:"btn btn-outline-success ms-1 mb-3",onClick:b},"회원가입")])])])])]))}};export{j as default};
