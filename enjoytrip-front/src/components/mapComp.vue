<script setup>
import {onMounted, ref} from "vue";

const loadScript = () => {
  const key = import.meta.env.VITE_KAKAOMAP_KEY
  const script = document.createElement('script')

  script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${key}&autoload=false`
  script.addEventListener('load', () => kakao.maps.load(initMap))
  document.head.appendChild(script)
}

let map = null;
const container = ref(null);
const initMap = () => {
  const options = {
    center: new kakao.maps.LatLng(37.506502, 127.053617),
    level: 5,
  }
  map = new kakao.maps.Map(container.value, options);
}

onMounted(() => {
  if (window.kakao?.maps) {
    initMap()
  } else {
    loadScript()
  }
})
</script>



<template>
<div class="wrapper">
  <div ref="container"></div>
</div>
</template>

<style scoped>
.wrapper {
  background: greenyellow;
  height: 100%;
  width: 100%;

  & div:first-child {
    width: 100%;
    height: 500px;
  }
}
</style>