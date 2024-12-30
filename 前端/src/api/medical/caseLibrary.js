import request from '@/utils/request'

const api_name = '/admin/medical/caseLibrary'

export default {
  list() {
    return request({
      url: `${api_name}/list`,
      method: 'GET'
    })
  },
  listPage(data){
    return request({
      url: `${api_name}/list/page`,
      method: 'GET',
      params: data
    })
  },
  statistics(){
    return request({
      url: `${api_name}/statistics`,
      method: 'GET'
    })
  },
  addCaseLibrary(data) {
    return request({
      url: `${api_name}/addCaseLibrary`,
      method: 'POST',
      data: data
    })
  },
  deleteCaseLibrary(id) {
    return request({
      url: `${api_name}/deleteCaseLibrary/${id}`,
      method: 'DELETE'
    })
  }
}
