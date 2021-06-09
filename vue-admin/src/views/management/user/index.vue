<template>
  <el-table v-loading="loading" :data="tableData" stripe style="width: 100%">
    <el-table-column prop="user" label="账号" width="180"> </el-table-column>
    <el-table-column prop="name" label="名称" width="180"> </el-table-column>
    <el-table-column prop="note" label="备注"> </el-table-column>
  </el-table>
</template>
<script>
import userApi from '@/api/user'
export default {
  data() {
    return {
      tableData: [],
      loading: false
    }
  },
  mounted() {
    this.loading = true
    userApi
      .GetUserGetUserAll()
      .then((res) => {
        this.tableData = res.data.data
        this.loading = false
      })
      .catch((e) => {
        console.log(e)
        this.loading = false
      })
  }
}
</script>
<style scoped lang="scss"></style>
