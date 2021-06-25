<template>
  <div class="center">
    <search-table @onRefresh="refreshTable" @onSearch="refreshTable" />
    <el-row>
      <wb-button type="primary" size="mini" @click="importData" upload>导入</wb-button>
      <el-button type="primary" size="mini" @click="exportData">导出</el-button>
      <el-button type="primary" size="mini" @click="addTable">新键</el-button>
      <el-button type="danger" size="mini" @click="deleteTable">删除</el-button>
    </el-row>
    <el-table @selection-change="selectTable" border v-loading="loading" :data="tableData" stripe style="width: 100%">
      <el-table-column type="index" width="50" label="序号" />
      <el-table-column type="selection" width="55" />
      <el-table-column prop="value" label="值" width="180"> </el-table-column>
      <el-table-column prop="user" label="用户" width="180"> </el-table-column>
      <el-table-column prop="note" label="备注"> </el-table-column>
      <el-table-column fixed="right" label="操作" width="250">
        <template slot-scope="scope">
          <el-button @click="showEdit(scope.row)" type="primary" size="mini">查看</el-button>
          <el-button type="danger" size="mini" @click="deleteTable(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="selectSizeChange"
      @current-change="selectCurrentChange"
      :current-page.sync="searchParams.page"
      :page-sizes="[2, 10, 20, 40]"
      :page-size="searchParams.pageSize"
      layout="total,sizes, prev, pager, next"
      :total="tableDataSize"
    >
    </el-pagination>
    <edit-table ref="editTable" @Submit="submit" />
  </div>
</template>
<script>
import tableApi from '@/api/table'
import EditTable from './component/edit-table.vue'
import SearchTable from './component/search-table.vue'
import { $Loading, dowBlobFile } from '@/utils'

export default {
  components: { EditTable, SearchTable },
  data() {
    return {
      tableData: [],
      tableDataSize: 0,
      searchParams: {
        page: 1,
        pageSize: 2,
        sort: 'DESC',
        sortKey: 'id'
      },
      selectTableData: [], //选中后的
      syncData: [],
      loading: false
    }
  },
  mounted() {
    this.loading = true
    this.refreshTable()
  },
  methods: {
    //导入
    importData(file) {
      this.loading = true
      tableApi
        .PostTableImportData({ file })
        .then((res) => {
          const { data } = res
          if (data.code === 200) {
            this.$message.success(data.msg)
          }
          this.refreshTable()
        })
        .catch((e) => {
          this.loading = false
          this.$message.error(String(e))
        })
    },
    //导出
    exportData() {
      this.loading = true
      dowBlobFile(tableApi.PostTableExportData, this.searchParams, '表格', '.xlsx')
        .then((res) => {
          this.$message.success('操作成功!')
          this.loading = false
        })
        .catch((e) => {
          this.$message.error(String(e))
          this.loading = false
        })
    },
    selectSizeChange(val) {
      this.searchParams.pageSize = val
      this.refreshTable()
    },
    selectCurrentChange(val) {
      this.refreshTable()
    },
    //选中行
    selectTable(val) {
      this.selectTableData = val
    },
    showEdit(item) {
      //获取权限
      //   const loading = $Loading()
      this.$refs.editTable.showEdit(item)
    },
    addTable() {
      this.$refs.editTable.showEdit()
    },
    refreshTable(params) {
      if (params) this.searchParams = params
      this.tableData = []
      this.selectTableData = []
      this.loading = true
      tableApi
        .PostTableGetTableAll(this.searchParams)
        .then((res) => {
          const { data } = res
          this.tableData = data.data.list
          this.tableDataSize = data.data.size
          this.loading = false
        })
        .catch((e) => {
          console.log(e)
          this.loading = false
        })
    },
    submit({ form, tableAll }) {
      const loading = $Loading()
      tableApi
        .PostTableSaveTable(form)
        .then((res) => {
          const { data } = res
          if (data.code === 200) {
            this.$refs.editTable.closeDialog()
            this.$message.success(data.msg)
          } else this.$message.error(data.msg)
          loading.close()
          this.refreshTable()
        })
        .catch((e) => {
          this.$message.error(String(e))
          loading.close()
          this.$refs.editTable.closeDialog()
        })
    },
    deleteTable(row) {
      //批量删除-单个删除
      let params = {}
      if (!row.id) {
        //多个删除
        if (this.selectTableData.length > 0) {
          params.tableIds = this.selectTableData.map((item) => item.id)
        } else {
          this.$message.warning('未选择行!')
        }
      } else {
        //单个删除
        params.tableIds = [row.id]
      }
      this.$confirm('确认删除用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          tableApi
            .PostTableDeleteTable({ ...params })
            .then((res) => {
              const { data } = res
              this.refreshTable()
              if (data.code === 200) this.$message.success(data.msg)
            })
            .catch((e) => {
              console.log(e)
              this.$message.error(String(e))
            })
        })
        .catch(() => {})
    }
  }
}
</script>
<style scoped lang="scss">
.el-table {
  margin-top: 20px;
  margin-bottom: 20px;
  height: calc(100vh - 360px);
}
</style>
