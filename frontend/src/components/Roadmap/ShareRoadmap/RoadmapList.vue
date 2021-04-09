<template>
  <div class ="d-flex justify-content-center nanum-bold">
      <RoadmapListItem 
        v-for="(rm, idx) in rmlist" 
        :key="idx"
        id="rmli" 
        :sidx="idxlist[idx]"
        :roadmap="rm" 
        :username="unlist[idx]"

      />
  </div>
</template>

<script>
import RoadmapListItem from '@/components/Roadmap/ShareRoadmap/RoadmapListItem'
export default {
  props: {
      rmlist: Array,
      unlist: Array,
    },
  components: {
    RoadmapListItem
  },
  data(){
    return{
      idxlist: [],
      
    }
  },
  beforeMount(){
  },
  created() {
    for(let i = 0 ; i < this.rmlist.length ; i++)
      this.idxlist[i] = i;
  },
}
</script>

<style>
#rmli {
  width: 250px;
  height: 400px;
  margin: 10px; 
  overflow: hidden;
}
</style>