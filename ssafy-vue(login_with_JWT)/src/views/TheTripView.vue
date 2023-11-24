<template>
  <div class="container text-center mt-3">
    <div class="alert alert-info" role="alert">여행지 검색</div>
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
      <button id="btn-search" class="btn btn-outline-success" type="button" @click="searchTrips">검색</button>
    </form>
    <!-- 관광지 검색 end -->

    <!-- kakao map start -->
    <div style="width:100%;">
      <VKakaoMap :trips="trips" :selectedTrip="selectedTrip" style="margin: auto"/>
    </div>

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
          <tr v-for="trip in trips" :key="trip.title" @click="viewTrip(trip)">
            <td><img :src="trip.firstimage" width="100" /></td>
            <td>{{ trip.title }}</td>
            <td>{{ trip.addr1 }} {{ trip.addr2 }}</td>
            <td>
              <button class="btn btn-outline-secondary">추가</button>
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
import VKakaoMap from "@/components/common/VKakaoMap.vue";
// import kakao from "//dapi.kakao.com/v2/maps/sdk.js?appkey=63cd578ef6468c9ce78530d95cb5de9b&libraries=services,clusterer,drawing";
const { VITE_OPEN_API_SERVICE_KEY } = import.meta.env;

const baseUrl = "https://apis.data.go.kr/B551011/KorService1/";

onMounted(() => {
  makeOption();
  getTripLists();
});

const trips = ref([]);
const areas = ref([]);
const selectedTrip = ref({});

const viewTrip = (select) => {
  selectedTrip.value = select;
};

const getTripLists = async () => {
  const res = await axios
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
    .catch((err) => {
      console.log(err);
    });

  trips.value = res.data.response?.body.items.item;
  console.log(res);
};

const makeOption = async () => {
  const res = await axios
    .get(baseUrl + "areaCode1", {
      params: {
        serviceKey: VITE_OPEN_API_SERVICE_KEY,
        MobileOS: "ETC",
        MobileApp: "AppTest",
        _type: "json",
      },
    })
    .catch((err) => {
      console.log(err);
    });
  areas.value = res.data.response?.body.items.item;
};

// 검색
const searchArea = ref("0");
const searchContentId = ref("0");
const searchKeyword = ref([]);

const searchTrips = async () => {
  if (searchKeyword.value == "") {
    const res = await axios
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
      .catch((err) => {
        console.log(err);
      });
    trips.value = res.data.response?.body.items.item;
  } else {
    const res = await axios
      .get(baseUrl + "searchKeyword1", {
        params: {
          serviceKey: VITE_OPEN_API_SERVICE_KEY,
          areaCode: searchArea.value == "0" ? "" : searchArea.value,
          contentTypeId: searchContentId.value == "0" ? "" : searchContentId.value,
          keyword: searchKeyword.value,
          MobileOS: "ETC",
          MobileApp: "AppTest",
          _type: "json",
        },
      })
      .catch((err) => {
        console.log(err);
      });
    trips.value = res.data.response?.body.items.item;
  }
};
</script>
<style></style>
