import React, { useState, useEffect, ChangeEvent } from "react";
import { Link } from "react-router-dom";
import PersonService from "../services/person-service";
import Person from "../types/person";

const PersonList = () => {
  const [persons, setPersons] = useState<Array<Person>>([]);
  const [currentPerson, setCurrentPerson] = useState<Person | null>(null);
  const [currentIndex, setCurrentIndex] = useState<number>(-1);
  const [searchFirstName, setSearchFirstName] = useState<string>("");

  useEffect(() => {
    retrievePersons();
  }, []);

  const onChangeSearchTitle = (e: ChangeEvent<HTMLInputElement>) => {
    const searchFirstName = e.target.value;
    setSearchFirstName(searchFirstName);
  };

  const retrievePersons = () => {
    PersonService.getAll()
      .then((response: any) => {
        setPersons(response.data);
        console.log(response.data);
      })
      .catch((e: Error) => {
        console.log(e);
      });
  };

  const refreshList = () => {
    retrievePersons();
    setCurrentPerson(null);
    setCurrentIndex(-1);
  };

  const setActivePerson = (person: Person, index: number) => {
    setCurrentPerson(person);
    setCurrentIndex(index);
  };

  const removeAllPeople = () => {
    PersonService.removeAll()
      .then((response: any) => {
        console.log(response.data);
        refreshList();
      })
      .catch((e: Error) => {
        console.log(e);
      });
  };

  const findByFirstName = () => {
    PersonService.findByFirstName(searchFirstName)
      .then((response: any) => {
        setPersons(response.data);
        setCurrentPerson(null);
        setCurrentIndex(-1);
        console.log(response.data);
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
            className="form-control"
            placeholder="Search by First Name"
            value={searchFirstName}
            onChange={onChangeSearchTitle}
          />
          <div className="input-group-append">
            <button
              className="btn btn-outline-secondary"
              type="button"
              onClick={findByFirstName}
            >
              Search
            </button>
          </div>
        </div>
      </div>
      <div className="col-md-6">
        <h4>Person List</h4>

        <ul className="list-group">
          {persons &&
            persons.map((person, index) => (
              <li
                className={
                  "list-group-item " + (index === currentIndex ? "active" : "")
                }
                onClick={() => setActivePerson(person, index)}
                key={index}
              >
                {person.firstName}
              </li>
            ))}
        </ul>

        <button
          className="m-3 btn btn-sm btn-danger"
          onClick={removeAllPeople}
        >
          Remove All
        </button>
      </div>
      <div className="col-md-6">
        {currentPerson ? (
          <div>
            <h4>Person</h4>
            <div>
              <label>
                <strong>First Name:</strong>
              </label>{" "}
              {currentPerson.firstName}
            </div>
            <div>
              <label>
                <strong>Last Name:</strong>
              </label>{" "}
              {currentPerson.lastName}
            </div>

            <Link
              to={"/person/" + currentPerson.id}
              className="badge badge-warning"
            >
              Edit
            </Link>
          </div>
        ) : (
          <div>
            <br />
            <p>Please click on a Person...</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default PersonList;
