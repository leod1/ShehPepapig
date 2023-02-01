<template>
  <ul>
    <li v-for="dish in dishes" :key="dish.id">


    </li>
  </ul>
</template>

<script>
export default {
  name: "DishList",
  data() {
    dishes: []
  }
  
    created() {
    await fetch('http://localhost:3000/dishes')
      .then(response => response.json())
      .then(dishes => {
        this.dishes = dishes;
      });
  }

}
</script>
