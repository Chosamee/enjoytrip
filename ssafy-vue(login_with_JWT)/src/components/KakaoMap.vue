<template>
  <div id="map" class="mt-3" style="width: 100%; height: 400px"></div>
</template>

<script setup>
import { toRaw } from "vue";
import { ref, onMounted } from "vue";
// https://codesandbox.io/s/nervous-keldysh-87yxg?file=/src/components/KakaoMap.vue
onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    initMap();
  } else {
    const script = document.createElement("script");
    /* global kakao */
    script.onload = () => kakao.maps.load(initMap());
    script.src =
      "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=2eba1ed7916afa21d299d9d412dd2d43";
    document.head.appendChild(script);
  }
});

let map, markers;

const initMap = () => {
  kakao.maps.load(() => {
    const container = document.getElementById("map");
    const options = {
      center: new kakao.maps.LatLng(33.450701, 126.570667),
      level: 3,
    };
    map = new kakao.maps.Map(container, options);
  });
};

const displayMarker = (markerPositions) => {
  if (markers.length > 0) {
    markers.forEach((marker) => marker.setMap(null));
  }

  const positions = markerPositions.map((position) => new kakao.maps.LatLng(...position));

  if (positions.length > 0) {
    markers = positions.map(
      (position) =>
        new kakao.maps.Marker({
          map: toRaw(map),
          position,
        })
    );

    const bounds = positions.reduce(
      (bounds, latlng) => bounds.extend(latlng),
      new kakao.maps.LatLngBounds()
    );

    toRaw(map).setBounds(bounds);
  }
};

const displayInfoWindow = () => {
  if (this.infowindow && this.infowindow.getMap()) {
    //이미 생성한 인포윈도우가 있기 때문에 지도 중심좌표를 인포윈도우 좌표로 이동시킨다.
    toRaw(this.map).setCenter(this.infowindow.getPosition());
    return;
  }

  var iwContent = '<div style="padding:5px;">Hello World!</div>', // 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
    iwPosition = new kakao.maps.LatLng(33.450701, 126.570667), //인포윈도우 표시 위치입니다
    iwRemoveable = true; // removeable 속성을 ture 로 설정하면 인포윈도우를 닫을 수 있는 x버튼이 표시됩니다

  this.infowindow = new kakao.maps.InfoWindow({
    map: toRaw(this.map), // 인포윈도우가 표시될 지도
    position: iwPosition,
    content: iwContent,
    removable: iwRemoveable,
  });

  toRaw(this.map).setCenter(iwPosition);
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
#map {
  width: 400px;
  height: 400px;
}

.button-group {
  margin: 10px 0px;
}

button {
  margin: 0 3px;
}
</style>
