<script setup>
import { ref, watch } from "vue";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";
import { useMemberStore } from "@/stores/member";
import { useMenuStore } from "@/stores/menu";

const router = useRouter();

const memberStore = useMemberStore();

const { isLogin } = storeToRefs(memberStore);
const { userLogin, getUserInfo } = memberStore;
const { changeMenuState } = useMenuStore();

const loginUser = ref({
  email: "",
  password: "",
});

function checkValidation() {
  if (loginUser.value.email === "") {
    alert("아이디를 입력해주세요.");
    return false;
  }
  else if (!loginUser.value.email.includes("@")) {
    alert("이메일 형식이 아닙니다.");
    return false;
  } // 비밀번호 유효성 검사 (최소 8자, 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자)
  else if (!loginUser.value.password.match(/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,25}$/)) {
    alert("비밀번호는 최소 8자, 최소 하나의 문자, 하나의 숫자 및 하나의 특수 문자를 입력해주세요.");
    return false;
  }
  else if (loginUser.value.password === "") {
    alert("비밀번호를 입력해주세요.");
    return false;
  }
  return true;
}

const login = async () => {
  if (!checkValidation()) return;
  await userLogin(loginUser.value);
  let token = sessionStorage.getItem("accessToken");
  if (isLogin.value) {
    getUserInfo(token);
    changeMenuState();
    router.push({ name: "main" });
  } else {
    alert("아이디 또는 비밀번호가 일치하지 않습니다.");
    router.push({name: "user-login"})
  }
};

const regist = () => {
  router.push({name: "user-join"});
};
</script>

<template>
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-lg-10">
        <h2 class="my-3 py-3 shadow-sm bg-light text-center">
          <mark class="orange">로그인</mark>
        </h2>
      </div>
      <div class="col-lg-10">
        <form>
          <div class="form-check mb-3 float-end">
            <input class="form-check-input" type="checkbox" />
            <label class="form-check-label" for="saveid"> 아이디저장 </label>
          </div>
          <div class="mb-3 text-start">
            <label for="userid" class="form-label">아이디 : </label>
            <input type="text" class="form-control" v-model="loginUser.email" placeholder="아이디..." />
          </div>
          <div class="mb-3 text-start">
            <label for="userpwd" class="form-label">비밀번호 : </label>
            <input type="password" class="form-control" v-model="loginUser.password" @keyup.enter="login" placeholder="비밀번호..." />
          </div>
          <div class="col-auto text-center">
            <button type="button" class="btn btn-outline-primary mb-3" @click="login">로그인</button>
            <button type="button" class="btn btn-outline-success ms-1 mb-3" @click="regist">회원가입</button>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<style scoped></style>
