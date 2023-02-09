import React, { useState, useEffect, ChangeEvent } from "react";
import TodoService from "../services/todo-service";
import Todo from "../types/todo";

const TodosList = () => {
  const initialTodoState = {
    id: null,
    name: "",
    active: true,
    editable: false
  };

  const [todos, setTodos] = useState<Array<Todo>>([]);
  const [currentIndex, setCurrentIndex] = useState<number>(-1);
  const [currentTodoItem, setCurrentTodoItem] = useState<Todo>(initialTodoState);
  const [newTodo, setNewTodoItem] = useState<string>("");

  useEffect(() => {
    retrieveTodos();
  }, []);

  const handleUpdateInputChange = (event: ChangeEvent<HTMLInputElement>) => {
    setCurrentTodoItem({ ...currentTodoItem, [event.target.name]: event.target.value });
  };

  const handleCheckboxInputChange = (event: ChangeEvent<HTMLInputElement>, todo: Todo, index: number) => {
    todo.active = event.target.checked != true;
    updateTodo(todo);
  };

  const handleCreateInputChange = (event: ChangeEvent<HTMLInputElement>) => {
    const newTodoName = event.target.value;
    setNewTodoItem(newTodoName);
  };

  const createTodo = () => {
    var data = {
      name: newTodo,
      active: true
    };

    TodoService.create(data)
      .then((response: any) => {
        setNewTodoItem("");
        retrieveTodos();
      })
      .catch((e: Error) => {
        console.log(e);
      });
  };

  const removeTodo = () => {
    TodoService.remove(currentTodoItem.id)
      .then(() => {
        refreshList();
      })
  }

  const filterAll = () => {
    refreshList();
  }

  const filterActive = () => {
    TodoService.getAll()
      .then((response: any) => {
        setTodos(response.data.filter(x => x.active === true));
      })
      .catch((e: Error) => {
        console.log(e);
      });
  }

  const filterComplete = () => {
    TodoService.getAll()
      .then((response: any) => {
        setTodos(response.data.filter(x => x.active === false));
      })
      .catch((e: Error) => {
        console.log(e);
      });
  }

  const updateTodo = (todo: Todo) => {
    //pulling off editable field, server doesn't want it (probably should come up with a better solution than stripping off the field.)
    var data = {
      id: todo.id,
      name: todo.name,
      active: todo.active
    };

    TodoService.update(data.id, data)
      .then(() => {
        refreshList();
      })
      .catch((e: Error) => {
        console.log(e);
      });
  };

  const retrieveTodos = () => {
    TodoService.getAll()
      .then((response: any) => {
        setTodos(response.data);
      })
      .catch((e: Error) => {
        console.log(e);
      });
  };

  const onCreateKeyUp = (e: React.KeyboardEvent<HTMLDivElement>) => {
    // allow user to submit by pressing enter key
    if (e.key === "Enter") {
      createTodo();
    }
  }

  const onUpdateKeyUp = (e: React.KeyboardEvent<HTMLDivElement>) => {
    // allow user to submit by pressing enter key
    if (e.key === "Enter") {
      updateTodo(currentTodoItem);
    }
  }

  const refreshList = () => {
    retrieveTodos();
    setCurrentTodoItem(initialTodoState);
    setCurrentIndex(-1);
  };

  const setActiveTodo = (evt, todo: Todo, index: number) => {
    if (evt.shiftKey) {
      if (!!todo.editable && todo.editable === true) {
        refreshList();
      } else {
        todo.editable = true;
        setCurrentTodoItem(todo);
        setCurrentIndex(index);
      }
    }
  };

  const afterSubmission = (event) => {
    event.preventDefault();
  }

  const removeAllTodos = () => {
    TodoService.removeAll()
      .then(() => {
        refreshList();
      })
      .catch((e: Error) => {
        console.log(e);
      });
  };

  return (
    <div className="list row">
      <div className="col-md-8">
        <div className="input-group mb-3">
          <input
            type="text"
            id="name"
            name="name"
            className="form-control"
            placeholder="What needs to be done?"
            value={newTodo}
            onChange={handleCreateInputChange}
            onKeyUp={onCreateKeyUp}
          />
        </div>
      </div>
      <div className="col-md-6">
        <h4>Todo List</h4>
        <button
          className="m-3 btn btn-sm btn-primary"
          onClick={filterAll}
        >
          All
        </button>
        <button
          className="m-3 btn btn-sm btn-warning"
          onClick={filterActive}
        >
          Active
        </button>
        <button
          className="m-3 btn btn-sm btn-success"
          onClick={filterComplete}
        >
          Completed
        </button>
        <div>Shift-click a Todo item to edit or delete it.</div>
        <ul className="list-group">
          {todos &&
            todos.map((todo, index) => (
              <li
                className={
                  "list-group-item " + (index === currentIndex ? "active-todo" : "")
                }
                onClick={(e) => { setActiveTodo(e, todo, index)}}
                key={index}
              >
                <div className="row">
                  <form className = "form-inline" onSubmit={afterSubmission}>
                    <div className="form-group col-md-2">
                      <input type="checkbox" checked={!todo.active} onChange={(e) => {handleCheckboxInputChange(e, todo, index)}}/>
                    </div>
                    <div className="form-group col-md-7">
                      <input
                        type="text"
                        disabled={!todo.editable}
                        className={(index === currentIndex ? "form-control" : "hide")}
                        id="name"
                        name="name"
                        value={currentTodoItem.name}
                        onChange={handleUpdateInputChange}
                        onKeyUp={onUpdateKeyUp}
                        key={index}
                      />
                    </div>
                  </form>
                  <div className={(index !== currentIndex ? "" + (!todo.active ? "strikethrough" : "") : "hide")}>{todo.name}</div>
                </div>
              </li>
            ))}
        </ul>

        <div>{todos.filter(x => x.active === true).length} items remaining</div>

        <button
          className="m-3 btn btn-sm btn-danger"
          onClick={removeAllTodos}
        >
          Remove All
        </button>
        <button
          className={(currentIndex !== -1 ? "m-3 btn btn-sm btn-dark" : "hide")}
          onClick={removeTodo}
        >
          Remove This Todo
        </button>
      </div>
    </div>
  );
};

export default TodosList;
