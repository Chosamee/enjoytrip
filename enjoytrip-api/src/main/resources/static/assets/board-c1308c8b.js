import{B as e}from"./index-9efc226b.js";const a=e();function n(t,c,o){a.get("/board",{params:t}).then(c).catch(o)}function r(t,c,o){a.get(`/board/${t}`).then(c).catch(o)}function l(t,c,o){console.log("boardjs article",t),a.post("/board",JSON.stringify(t)).then(c).catch(o)}function s(t,c,o){a.get(`/board/modify/${t}`).then(c).catch(o)}function d(t,c,o){a.put("/board",JSON.stringify(t)).then(c).catch(o)}function f(t,c,o){a.delete(`/board/${t}`).then(c).catch(o)}export{f as a,r as d,s as g,n as l,d as m,l as r};
