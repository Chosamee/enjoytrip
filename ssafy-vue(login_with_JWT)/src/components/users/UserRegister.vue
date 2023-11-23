<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";

const router = useRouter();

const memberStore = useMemberStore();

const { userRegist } = memberStore;
const registUser = ref({
  email: "",
  domain: "",
  password: "",
  passwordCheck: "",
  name: "",
});

function checkValidation() {
  if (registUser.value.email === "") {
    alert("이메일을 입력해주세요.");
    return false;
  }
  else if (registUser.value.domain === "") {
    alert("이메일 도메인을 선택해주세요.");
    return false;
  }
  else if (registUser.value.name === "") {
    alert("이름을 입력해주세요.");
    return false;
  }
  else if (registUser.value.password === "") {
    alert("비밀번호를 입력해주세요.");
    return false;
  }
  else if (registUser.value.passwordCheck === "") {
    alert("비밀번호 확인을 입력해주세요.");
    return false;
  }
  else if (registUser.value.password !== registUser.value.passwordCheck) {
    alert("비밀번호가 일치하지 않습니다.");
    return false;
  }
  // 비밀번호 유효성 검사 (최소 8자, 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자)
  else if (!registUser.value.password.match(/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/)) {
    alert("비밀번호는 최소 8자, 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자를 입력해주세요.");
    return false;
  }
  return true;
}

const regist = async () => {
  if (!checkValidation()) return;
  const loginForm = {
    email: registUser.value.email + "@" + registUser.value.domain,
    password: registUser.value.password,
    name: registUser.value.name,
  };
  await userRegist(loginForm);
};

const showPwd = () => {
  document.querySelector("#userpwd").setAttribute("type", "text");
};
const hidePwd = () => {
  document.querySelector("#userpwd").setAttribute("type", "password");
};
const showPwdCheck = () => {
  document.querySelector("#pwdcheck").setAttribute("type", "text");
};
const hidePwdCheck = () => {
  document.querySelector("#pwdcheck").setAttribute("type", "password");
};

</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">회원가입</mark>
        </h2>
      </div>
      <div class="col-lg-10 text-start">
        <form>
          <div class="mb-3">
            <label for="email" class="form-label">이메일 : </label>
            <div class="input-group">
              <input type="text" class="form-control" v-model="registUser.email" placeholder="이메일아이디"/>
              <span class="input-group-text">@</span>
              <select class="form-select" aria-label="이메일 도메인 선택" v-model="registUser.domain">
                <option disabled value="">선택</option>
                <option value="ssafy.com">싸피</option>
                <option value="google.com">구글</option>
                <option value="naver.com">네이버</option>
                <option value="kakao.com">카카오</option>
              </select>
            </div>
          </div>
          <div class="mb-3">
            <label for="username" class="form-label">이름 : </label>
            <input type="text" class="form-control" v-model="registUser.name" placeholder="이름..." />
          </div>
          <!--          <div class="mb-3">
            <label for="userid" class="form-label">아이디 : </label>
            <input type="text" class="form-control" v-model="registUser.email" placeholder="아이디..." />
          </div>-->
          <div class="mb-3">
            <label for="userpwd" class="form-label">비밀번호 : </label>
            <input id="userpwd" type="password" class="form-control" v-model="registUser.password" placeholder="비밀번호..." />
            <button type="button" class="btn" @mousedown.left="showPwd" @mouseup.left="hidePwd">비밀번호 보기</button>
          </div>
          <div class="mb-3">
            <label for="pwdcheck" class="form-label">비밀번호확인 : </label>
            <input id="pwdcheck" type="password" class="form-control" v-model="registUser.passwordCheck" placeholder="비밀번호확인..." />
            <button type="button" class="btn" @mousedown.left="showPwdCheck" @mouseup.left="hidePwdCheck">비밀번호 확인 보기</button>
          </div>

          <div class="col-auto text-center">
            <button type="button" class="btn btn-outline-primary mb-3" @click="regist">회원가입</button>
            <!--            <button type="button" class="btn btn-outline-success ms-1 mb-3">초기화</button>-->
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
