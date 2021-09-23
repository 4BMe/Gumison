<template>
  <div>hi</div>
  <Footer/>
</template>

<script>
import Footer from '@/components/Footer.vue';
require('../templates/scripts/custom.js');

export default {
  name: 'App',
  components: {
    Footer,
  }
}
</script>

<style>
  @import '../templates/styles/bootstrap.css';
  @import '../templates/styles/style.css';
  @import 'https://fonts.googleapis.com/css2?family=Inter:wght@100;200;300;400;500;600;700;800;900&display=swap';
  @import '../templates/fonts/css/fontawesome-all.min.css';
</style>
