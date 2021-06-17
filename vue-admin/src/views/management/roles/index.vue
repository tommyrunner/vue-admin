<template>
  <div class="center">
    <search-roles @onRefresh="refreshTable" @onSearch="refreshTable" />
    <el-row>
      <el-button type="primary" size="mini" @click="addUser">新键</el-button>
      <el-button type="warning" size="mini" @click="syncRoles" title="将客户端路由列表同步到服务器中">同步</el-button>
      <el-button type="danger" size="mini" @click="deleteUser">删除</el-button>
    </el-row>
    <el-table @selection-change="selectTable" border v-loading="loading" :data="tableData" stripe style="width: 100%">
      <el-table-column type="index" width="50" label="序号" />
      <el-table-column type="selection" width="55" />
      <el-table-column prop="title" label="标题" width="180"> </el-table-column>
      <el-table-column prop="roles" label="菜单" width="180"> </el-table-column>
      <el-table-column prop="path" label="地址" width="180"> </el-table-column>
      <el-table-column prop="icon" label="图标" width="180"> </el-table-column>
      <el-table-column prop="note" label="备注"> </el-table-column>
      <el-table-column fixed="right" label="操作" width="250">
        <template slot-scope="scope">
          <el-button @click="showEditUser(scope.row)" type="primary" size="mini">查看</el-button>
          <el-button type="danger" size="mini" @click="deleteUser(scope.row)">删除</el-button>
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
    <edit-roles ref="editUser" @Submit="submit" />
  </div>
</template>
<script>
import rolesApi from '@/api/roles'
import EditRoles from './component/edit-roles.vue'
import SearchRoles from './component/search-roles.vue'
import { rolesRoutes } from '@/router'
import { $Loading } from '@/utils'

export default {
  components: { EditRoles, SearchRoles },
  data() {
    return {
      tableData: [],
      tableDataSize: 0,
      searchParams: {
        page: 1,
        pageSize: 2,
        sort: 'DESC',
        sortKey: 'id',
        user: '',
        name: ''
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
    showEditUser(item) {
      //获取权限
      //   const loading = $Loading()
      this.$refs.editUser.showEdit(item)
    },
    addUser() {
      this.$refs.editUser.showEdit()
    },
    //同步路由
    syncRoles() {
      this.syncData = []
      const loading = $Loading()
      this.toBaisRoles(rolesRoutes, this.syncData)
      rolesApi
        .PostRolesSyncRoles(this.syncData)
        .then((res) => {
          const { data } = res
          if (data.code === 200) {
            this.$message.success(data.msg)
          } else this.$message.error(data.msg)
          loading.close()
          this.refreshTable()
        })
        .catch((e) => {
          this.$message.error(String(e))
          loading.close()
          this.refreshTable()
        })
    },
    //递归路由为平层
    toBaisRoles(roles) {
      roles.forEach((item) => {
        if (!item.children && item.meta) {
          this.syncData.push({ title: item.meta.title, roles: item.name, path: item.path, icon: item.meta.icon, note: '同步添加' })
        }
        if (item.children && item.children.length > 0) {
          this.toBaisRoles(item.children)
        }
      })
    },
    refreshTable(params) {
      if (!params) params = this.searchParams
      this.searchParams = { ...params }
      this.tableData = []
      this.selectTableData = []
      this.loading = true
      rolesApi
        .PostRolesGetRolesAll(params)
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
    submit({ form, rolesAll }) {
      const loading = $Loading()
      rolesApi
        .PostRolesSaveRoles(form)
        .then((res) => {
          const { data } = res
          if (data.code === 200) {
            this.$refs.editUser.closeDialog()
            this.$message.success(data.msg)
          } else this.$message.error(data.msg)
          loading.close()
          this.refreshTable()
        })
        .catch((e) => {
          this.$message.error(String(e))
          loading.close()
          this.$refs.editUser.closeDialog()
        })
    },
    deleteUser(row) {
      //批量删除-单个删除
      let params = {}
      if (!row.id) {
        //多个删除
        if (this.selectTableData.length > 0) {
          params.rolesIds = this.selectTableData.map((item) => item.id)
        } else {
          this.$message.warning('未选择行!')
        }
      } else {
        //单个删除
        params.rolesIds = [row.id]
      }
      this.$confirm('确认删除用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          rolesApi
            .PostRolesDeleteRoles({ ...params })
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
