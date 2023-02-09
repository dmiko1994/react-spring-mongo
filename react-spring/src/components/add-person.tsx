import React, { useState, ChangeEvent } from "react";
import PersonService from "../services/person-service";
import Person from "../types/person";

const AddPerson = () => {
  const initialPersonState = {
    id: null,
    firstName: "",
    lastName: ""
  };
  const [person, setPerson] = useState<Person>(initialPersonState);
  const [submitted, setSubmitted] = useState<boolean>(false);

  const handleInputChange = (event: ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setPerson({ ...person, [name]: value });
  };

  const savePerson = () => {
    var data = {
      firstName: person.firstName,
      lastName: person.lastName
    };

    PersonService.create(data)
      .then((response: any) => {
        setPerson({
          id: response.data.id,
          firstName: response.data.firstName,
          lastName: response.data.lastName
        });
        setSubmitted(true);
        console.log(response.data);
      })
      .catch((e: Error) => {
        console.log(e);
      });
  };

  const newPerson = () => {
    setPerson(initialPersonState);
    setSubmitted(false);
  };

  return (
    <div className="submit-form">
      {submitted ? (
        <div>
          <h4>You submitted successfully!</h4>
          <button className="btn btn-success" onClick={newPerson}>
            Add
          </button>
        </div>
      ) : (
        <div>
          <div className="form-group">
            <label htmlFor="firstName">First Name</label>
            <input
              type="text"
              className="form-control"
              id="firstName"
              required
              value={person.firstName}
              onChange={handleInputChange}
              name="firstName"
            />
          </div>

          <div className="form-group">
            <label htmlFor="lastName">Last Name</label>
            <input
              type="text"
              className="form-control"
              id="lastName"
              required
              value={person.lastName}
              onChange={handleInputChange}
              name="lastName"
            />
          </div>

          <button onClick={savePerson} className="btn btn-success">
            Submit
          </button>
        </div>
      )}
    </div>
  );
};

export default AddPerson;
