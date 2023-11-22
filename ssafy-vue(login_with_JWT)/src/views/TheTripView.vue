<template>
  <div class="container text-center mt-3">
    <div class="alert alert-info" role="alert">여행지 검색</div>
    <div style="height: 70px"></div>
    <div
      class="shadow-sm p-4 mb-4 bg-white display-6 mt-3 text-center fw-bold"
      role="alert"
      style="color: rgba(27, 27, 16, 0.705)">
      지역별 관광정보
    </div>

    <!-- 관광지 검색 start -->
    <form class="d-flex my-3" onsubmit="return false;" role="search">
      <!-- 지역 선택 -->
      <select v-model="searchArea" id="search-area" class="form-select me-2">
        <option value="0">검색 할 지역 선택</option>
        <option v-for="area in areas" :key="area.code" :value="area.code">
          {{ area.name }}
        </option>
      </select>
      <!-- 유형 선택 -->
      <select v-model="searchContentId" id="search-content-id" class="form-select me-2">
        <option value="0">관광지 유형</option>
        <option value="12">관광지</option>
        <option value="14">문화시설</option>
        <option value="15">축제공연행사</option>
        <option value="25">여행코스</option>
        <option value="28">레포츠</option>
        <option value="32">숙박</option>
        <option value="38">쇼핑</option>
        <option value="39">음식점</option>
      </select>
      <!-- 검색어 입력 -->
      <input
        v-model="searchKeyword"
        id="search-keyword"
        class="form-control me-2"
        type="search"
        placeholder="검색어"
        aria-label="검색어" />
      <button id="btn-search" class="btn btn-outline-success" type="button" @click="searchTrips">
        검색
      </button>
    </form>
    <!-- 관광지 검색 end -->

    <!-- kakao map start -->
    <VKakaoMap :trips="trips" :selectedTrip="selectedTrip" />
    <!-- kakao map end -->

    <div class="row">
      <table class="table table-striped">
        <thead>
          <tr>
            <th>대표이미지</th>
            <th>관광지명</th>
            <th>주소</th>
            <th>북마크</th>
          </tr>
        </thead>
        <tbody id="trip-list">
          <tr
            v-for="(trip, tripIndex) in trips"
            :key="trip.title"
            @click="viewTrip(trip)">
            <td><img :src="trip.firstimage" width="100" /></td>
            <td>{{ trip.title }}</td>
            <td>{{ trip.addr1 }} {{ trip.addr2 }}</td>
            <td>
              <button class="btn btn-outline-secondary">
                추가
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import axios from "axios";
import KakaoMap from "@/components/KakaoMap.vue";
import VKakaoMap from "@/components/common/VKakaoMap.vue";
// import kakao from "//dapi.kakao.com/v2/maps/sdk.js?appkey=63cd578ef6468c9ce78530d95cb5de9b&libraries=services,clusterer,drawing";
const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;

const baseUrl = "https://apis.data.go.kr/B551011/KorService1/";

const selectedTrip = ref({});

onMounted(() => {
  makeOption();
  getTripLists();
});

const trips = ref([]);
const areas = ref([]);

const viewTrip = (trip) => {
  selectedTrip.value = trip;
}

const getTripLists = () => {
  axios
    .get(baseUrl + "areaBasedList1", {
      params: {
        serviceKey: VITE_OPEN_API_SERVICE_KEY,
        pageNo: 1,
        numOfRows: 20,
        MobileOS: "ETC",
        MobileApp: "AppTest",
        _type: "json",
      },
    })
    .then(({ data }) => {
      trips.value = data.response.body.items.item;
      console.log(data);
    })
    .catch((err) => {
      console.log(err);
    });
};

const makeOption = () => {
  axios
    .get(baseUrl + "areaCode1", {
      params: {
        serviceKey: VITE_OPEN_API_SERVICE_KEY,
        MobileOS: "ETC",
        MobileApp: "AppTest",
        _type: "json",
      },
    })
    .then(({ data }) => {
      areas.value = data.response.body.items.item;
    })
    .catch((err) => {
      console.log(err);
    });
};

// 검색
const searchArea = ref("0");
const searchContentId = ref("0");
const searchKeyword = ref([]);

const searchTrips = () => {
  if (searchKeyword.value == "") {
    axios
      .get(baseUrl + "areaBasedList1", {
        params: {
          serviceKey: VITE_OPEN_API_SERVICE_KEY,
          areaCode: searchArea.value == "0" ? "" : searchArea.value,
          contentTypeId: searchContentId.value == "0" ? "" : searchContentId.value,
          MobileOS: "ETC",
          MobileApp: "AppTest",
          _type: "json",
        },
      })
      .then(({ data }) => {
        trips.value = data.response.body.items.item;
      })
      .catch((err) => {
        console.log(err);
      });
  } else {
    axios
      .get(baseUrl + "searchKeyword1", {
        params: {
          serviceKey: VITE_OPEN_API_SERVICE_KEY,
          areaCode: searchArea.value == "0" ? "" : searchArea.value,
          contentTypeId: searchContentId.value == "0" ? "" : searchContentId.value,
          searchKeyword: searchKeyword.value,
          MobileOS: "ETC",
          MobileApp: "AppTest",
          _type: "json",
        },
      })
      .then(({ data }) => {
        trips.value = data.response.body.items.item;
      })
      .catch((err) => {
        console.log(err);
      });
  }
};

/*var positions; // marker 배열.
function makeList(data) {
  let markerInfo = {
    title: area.title,
    latlng: new kakao.maps.LatLng(area.mapy, area.mapx),
    content: `<div class="card m-1" style="max-width: 540px;">
                    <div class="row g-0">
                      <div class="col-md-4">
                        <img src=\${image} class="img-fluid rounded-start" alt="...">
                      </div>
                      <div class="col-md-8">
                        <div class="card-body">
                          <h5 class="card-title">\${area.title}</h5>
                          <p class="card-text">\${area.addr1}</p>
                          <p class="card-text"><small class="text-body-secondary">\${area.addr2}</small></p>
                        </div>
                      </div>
                    </div>
                  </div>`,
    /!*`<div class=""><h style="vertical-align: middle">${area.title}</h><img src="${area.firstimage}" width="73" height="70"></div>`*!/
  };
  positions.push(markerInfo);
  displayMarker();
}

function displayMarker() {
  // 마커 이미지의 이미지 주소입니다
  var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
  for (var i = 0; i < positions.length; i++) {
    // 마커 이미지의 이미지 크기 입니다
    var imageSize = new kakao.maps.Size(24, 35);

    // 마커 이미지를 생성합니다
    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

    // 마커를 생성합니다
    var marker = new kakao.maps.Marker({
      map: map, // 마커를 표시할 지도
      position: positions[i].latlng, // 마커를 표시할 위치
      title: positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
      image: markerImage, // 마커 이미지
    });
    var infowindow = new kakao.maps.InfoWindow({
      position: positions[i].latlng,
      content: positions[i].content,
    });

    kakao.maps.event.addListener(marker, "mouseover", makeOverListener(map, marker, infowindow));
    kakao.maps.event.addListener(marker, "mouseout", makeOutListener(infowindow));
  }

  // 첫번째 검색 정보를 이용하여 지도 중심을 이동 시킵니다
  map.setCenter(positions[0].latlng);
}

function makeOverListener(map, marker, infowindow) {
  return function () {
    infowindow.open(map, marker);
  };
}

// 인포윈도우를 닫는 클로저를 만드는 함수입니다
function makeOutListener(infowindow) {
  return function () {
    infowindow.close();
  };
}

function moveCenter(lat, lng) {
  // map.setCenter(new kakao.maps.LatLng(lat, lng));
}

var pollContent = "";
function addTravel(cnt) {
  // pollContent += `<div>\${trips[cnt].title}  <button class="btn btn-outline-danger">삭제</button></div>`;
  // document.querySelector("#poll-area").innerHTML = pollContent;
}*/
</script>
<style></style>
