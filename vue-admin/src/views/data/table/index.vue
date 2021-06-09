<template>
  <el-table v-loading="loading" :data="tableData" stripe style="width: 100%">
    <el-table-column prop="userId" label="用户" width="180"> </el-table-column>
    <el-table-column prop="value" label="值" width="180"> </el-table-column>
    <el-table-column prop="note" label="备注"> </el-table-column>
  </el-table>
</template>

<script>
import tableApi from '@/api/table'

export default {
  data() {
    return {
      tableData: [],
      loading: false
    }
  },
  mounted() {
    this.loading = true
    tableApi
      .GetTableGetTableAll()
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
