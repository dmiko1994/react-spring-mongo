import React from 'react';
import logo from './logo.svg';
import { Routes, Route, Link } from "react-router-dom";
import './App.css';
import "bootstrap/dist/css/bootstrap.min.css";
import PersonList from './components/person-list';
import AddPerson from './components/add-person';
import PersonViewer from './components/person-viewer';
import TodosList from './components/todos-list';

function App() {
  return (
    <div className="App">
      <script src="../dist/bundle.js"></script>
      <nav className="navbar navbar-expand navbar-dark bg-dark">
        <a href="/person" className="navbar-brand">
          Home
        </a>
        <div className="navbar-nav mr-auto">
          <li className="nav-item">
            <Link to={"/person"} className="nav-link">
              Persons
            </Link>
          </li>
          <li className="nav-item">
            <Link to={"/add"} className="nav-link">
              Add
            </Link>
          </li>
          <li className="nav-item">
            <Link to={"/todos"} className="nav-link">
              Todos
            </Link>
          </li>
        </div>
      </nav>

      <div className="container mt-3">
        <Routes>
          <Route path="/" element={<PersonList/>} />
          <Route path="/person" element={<PersonList/>} />
          <Route path="/add" element={<AddPerson/>} />
          <Route path="/person/:id" element={<PersonViewer/>} />
          <Route path="/todos" element={<TodosList/>} />
        </Routes>
      </div>

    </div>
  );
}

export default App;
