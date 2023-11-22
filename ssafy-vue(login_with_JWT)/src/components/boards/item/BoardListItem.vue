<script setup>
defineProps({ article: Object });
</script>

<template>
  <tr class="text-center">
    <th scope="row">{{ article.articleno }}</th>
    <td class="text-start">
      <router-link
        :to="{ name: 'article-view', params: { articleno: article.articleno } }"
        class="article-title link-dark"
      >
        {{ article.title }}
      </router-link>
    </td>
    <td>{{ article.email }}</td>
    <td>{{ article.hit }}</td>
    <td>{{ article.createdDate }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}
</style>
