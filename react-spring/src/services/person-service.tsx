import Person from "../types/person";
import http from "../http-common";

const getAll = () => {
  return http.get<Array<Person>>("/person");
};

const get = (id: any) => {
  return http.get<Person>(`/person/${id}`);
};

const create = (data: Person) => {
  return http.post<Person>("/person/insert", data);
};

const update = (id: any, data: Person) => {
  return http.patch<any>(`/person/${id}`, data);
};

const remove = (id: any) => {
  return http.delete<any>(`/person/delete/${id}`);
};

const removeAll = () => {
    return http.delete<any>("/person/delete");
  };

const findByFirstName = (firstName: string) => {
  return http.get<Array<Person>>(`/person/search/firstName/${firstName}`);
};

const PersonService = {
  getAll,
  get,
  create,
  update,
  remove,
  findByFirstName,
  removeAll
};

export default PersonService;
