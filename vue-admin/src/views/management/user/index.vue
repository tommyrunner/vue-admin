<template>
  <div class="center">
    <search-user />
    <el-row>
      <el-button type="primary" size="mini" @click="addUser">新键</el-button>
      <el-button type="danger" size="mini" @click="deleteUser">删除</el-button>
    </el-row>
    <el-table @selection-change="selectTable" border v-loading="loading" :data="tableData" stripe style="width: 100%">
      <el-table-column type="index" width="50" label="序号" />
      <el-table-column type="selection" width="55" />
      <el-table-column prop="user" label="账号" width="180"> </el-table-column>
      <el-table-column prop="name" label="名称" width="180"> </el-table-column>
      <el-table-column prop="note" label="备注"> </el-table-column>
      <el-table-column fixed="right" label="操作" width="200">
        <template slot-scope="scope">
          <el-button @click="showEditUser(scope.row)" type="primary" size="mini">查看</el-button>
          <el-button type="danger" size="mini" @click="deleteUser(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <edit-user ref="editUser" />
  </div>
</template>
<script>
import userApi from '@/api/user'
import EditUser from './component/edit-user.vue'
import SearchUser from './component/search-user.vue'
export default {
  components: { EditUser, SearchUser },
  data() {
    return {
      tableData: [],
      selectTableData: [], //选中后的
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
  },
  methods: {
    //选中行
    selectTable(val) {
      this.selectTableData = val
    },
    showEditUser(item) {
      this.$refs.editUser.showEdit(item)
    },
    addUser() {
      this.$refs.editUser.showEdit()
    },
    deleteUser(row) {
      //批量删除-单个删除
      if (!row.id) {
        //多个删除
        if (this.selectTableData.length > 0) {
        } else {
          this.$message.warning('未选择行!')
        }
      } else {
        //单个删除
        console.log(row)
      }
    }
  }
}
</script>
<style scoped lang="scss">
.el-table {
  margin-top: 20px;
}
</style>
