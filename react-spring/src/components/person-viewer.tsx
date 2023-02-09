import React, { useState, useEffect, ChangeEvent } from "react";
import { useParams, useNavigate } from 'react-router-dom';
import PersonService from "../services/person-service";
import Person from "../types/person";

const PersonViewer = () => {
  const { id }= useParams();
  let navigate = useNavigate();

  const initialPersonState = {
    id: null,
    firstName: "",
    lastName: ""
  };
  const [currentPerson, setCurrentPerson] = useState<Person>(initialPersonState);
  const [message, setMessage] = useState<string>("");

  const getPerson = (id: string) => {
    PersonService.get(id)
      .then((response: any) => {
        setCurrentPerson(response.data);
        console.log(response.data);
      })
      .catch((e: Error) => {
        console.log(e);
      });
  };

  useEffect(() => {
    if (id)
    getPerson(id);
  }, [id]);

  const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setCurrentPerson({ ...currentPerson, [name]: value });
  };

  const updatePerson = () => {
    PersonService.update(currentPerson.id, currentPerson)
      .then((response: any) => {
        console.log(response.data);
        navigate("/person");
      })
      .catch((e: Error) => {
        console.log(e);
      });
  };

  const deletePerson = () => {
    PersonService.remove(currentPerson.id)
      .then((response: any) => {
        console.log(response.data);
        navigate("/person");
      })
      .catch((e: Error) => {
        console.log(e);
      });
  };

  return (
    <div>
      {currentPerson ? (
        <div className="edit-form">
          <h4>Person</h4>
          <form>
            <div className="form-group">
              <label htmlFor="title">First Name</label>
              <input
                type="text"
                className="form-control"
                id="firstName"
                name="firstName"
                value={currentPerson.firstName}
                onChange={handleInputChange}
              />
            </div>
            <div className="form-group">
              <label htmlFor="description">Last Name</label>
              <input
                type="text"
                className="form-control"
                id="lastName"
                name="lastName"
                value={currentPerson.lastName}
                onChange={handleInputChange}
              />
            </div>
          </form>

          <button className="badge badge-danger mr-2" onClick={deletePerson}>
            Delete
          </button>

          <button
            type="submit"
            className="badge badge-success"
            onClick={updatePerson}
          >
            Update
          </button>
          <p>{message}</p>
        </div>
      ) : (
        <div>
          <br />
          <p>Please click on a Person...</p>
        </div>
      )}
    </div>
  );
};

export default PersonViewer;
