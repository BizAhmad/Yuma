import React, { Component } from 'react';
import {
  ListGroup, ListGroupItem,
  Container, Col, Form, FormGroup, Label, Input
} from 'reactstrap';
import ApiToken from '../middleware/ApiToken';

const API = "api/rest/company/"

class UserByCompanyName extends Component {

  constructor(props) {
    super(props);

    this.state = {
      userInput: "",
      apiObject: null,
      searchList: [],
    }

    this.handleQueryChange = this.handleQueryChange.bind(this);
    this.companyFetch = this.companyFetch.bind(this);
  }

  checkAuthenticated(){
    const apiToken = new ApiToken();
    if(!apiToken.isAuthenticated()){
      console.log('User Not Logged');
      this.props.history.push(`/Login`)
    }else{
      console.log('User Login Success');
    }
  }

  handleQueryChange(input){
    this.setState({
      userInput: input
    }, this.companyFetch(input));
    if( (this.state.apiObject === null) || (this.searchList = ["no result"])) {
      this.setState({
        searchList: ["no result"]
      })
    }
    else{
      this.search()
    }
  }

  companyFetch(company){
    console.log(company)
    fetch(API + company)
    .then((obj) => {
      this.setState({ apiObject: obj})
    })
  }

  search() {
    var matchedArr = [];
    for (var i = 0; i < this.state.apiObject.length; i++) {
      if (this.state.apiObject[i].company.toString().includes(this.state.value)
      ) {
        matchedArr.push("email: "  + this.state.apiObject[i].email.toString() +
                        "First Name: " + this.state.apiObject[i].firstname.toString() +
                        "Last Name: " + this.state.apiObject[i].lastname.toString());
      }
    }
    this.setState({ searchList: matchedArr });
  }

  render() {
    return (
      <Container>
        <Col sm="12" md={{ size: 6, offset: 3 }}>
          <Form className="form">
            <FormGroup>
              <input
                onChange = { (e)=>this.handleQueryChange(e.target.value)}
                value={this.state.userInput}
                type="text"
              />
            </FormGroup>
          </Form>
        </Col>
        <Col sm={{ size: 6, order: 2, offset: 1 }}>
          <ListGroup>
            {
              this.state.searchList.map(x => <ListGroupItem>{x}</ListGroupItem>)
            }
          </ListGroup>
        </Col>
      </Container>

    );
  }

}
export default UserByCompanyName;
