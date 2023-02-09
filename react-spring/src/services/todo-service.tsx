import http from "../http-common";
import Todo from "../types/todo";

const getAll = () => {
  return http.get<Array<Todo>>("/todos");
};

const create = (data: Todo) => {
  return http.post<Todo>("/todos/insert", data);
};

const update = (id: any, data: Todo) => {
  return http.patch<any>(`/todos/${id}`, data);
};

const remove = (id: any) => {
  return http.delete<any>(`/todos/delete/${id}`);
};

const removeAll = () => {
    return http.delete<any>("/todos/delete");
  };

const TodoService = {
  getAll,
  create,
  update,
  remove,
  removeAll
};

export default TodoService;
