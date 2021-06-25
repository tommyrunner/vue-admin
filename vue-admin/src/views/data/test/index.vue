<template>
  <div class="center">
    <search-test @onRefresh="refreshTable" @onSearch="refreshTable" />
    <el-row>
      <el-button type="primary" size="mini" @click="addTest">{{ $t('form.new') }}</el-button>
      <el-button type="danger" size="mini" @click="deleteTest">{{ $t('form.delete') }}</el-button>
    </el-row>
    <el-table @selection-change="selectTable" border v-loading="loading" :data="tableData" stripe style="width: 100%" empty-text="fsd">
      <el-table-column type="index" width="50" :label="$t('form.number')" />
      <el-table-column type="selection" width="55" />
      <el-table-column prop="value" label="值" width="180"> </el-table-column>
      <el-table-column prop="note" label="备注" width="180"> </el-table-column>
      <el-table-column fixed="right" :label="$t('form.operation')" width="250">
        <template slot-scope="scope">
          <el-button @click="showEdit(scope.row)" type="primary" size="mini">{{ $t('form.edit') }}</el-button>
          <el-button type="danger" size="mini" @click="deleteTest(scope.row)">{{ $t('form.delete') }}</el-button>
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
    <edit-test ref="editTest" @Submit="submit" />
  </div>
</template>
<script>
import testApi from '@/api/test'
import EditTest from './component/edit-test.vue'
import SearchTest from './component/search-test.vue'
import { $Loading } from '@/utils'

export default {
  components: { EditTest, SearchTest },
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
      this.$refs.editTest.showEdit(item)
    },
    addTest() {
      this.$refs.editTest.showEdit()
    },
    refreshTable(params) {
      if (!params) params = this.searchParams
      this.searchParams = { ...params }
      this.tableData = []
      this.selectTableData = []
      this.loading = true
      testApi
        .PostTestGetTestAll(params)
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
    submit({ form, testAll }) {
      const loading = $Loading()
      testApi
        .PostTestSaveTest(form)
        .then((res) => {
          const { data } = res
          if (data.code === 200) {
            this.$refs.editTest.closeDialog()
            this.$message.success(data.msg)
          } else this.$message.error(data.msg)
          loading.close()
          this.refreshTable()
        })
        .catch((e) => {
          this.$message.error(String(e))
          loading.close()
          this.$refs.editTest.closeDialog()
        })
    },
    deleteTest(row) {
      //批量删除-单个删除
      let params = {}
      if (!row.id) {
        //多个删除
        if (this.selectTableData.length > 0) {
          params.testIds = this.selectTableData.map((item) => item.id)
        } else {
          this.$message.warning('未选择行!')
        }
      } else {
        //单个删除
        params.testIds = [row.id]
      }
      this.$confirm('确认删除用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          testApi
            .PostTestDeleteTest({ ...params })
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
