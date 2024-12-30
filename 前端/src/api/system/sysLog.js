import request from '@/utils/request'

/*
登录日志管理相关的API请求函数
*/
const api_name = '/admin/system/sysLoginLog'

export default {

    /*
    获取权限(菜单/功能)列表
    */
    logPageByList(page, limit, data) {
        return request({
            url: `${api_name}/${page}/${limit}`,
            method: 'get',
            params: data
        })
    },

    delete(data) {
        return request({
            url: `${api_name}`,
            method: 'delete',
            data: data
        })
    }


}