import React, {Component} from 'react';
import ApiToken from "../middleware/ApiToken";

import {
  Card, CardHeader,  CardBody,
  CardDeck
} from 'reactstrap';
import AvailableMeals from "./AvailableMeals";
import DisplayMeals from "./DisplayMeals";
import DisplayUsers from "./DisplayUsers";
import MealCombo from "./MealCombo";

class Dashboard extends Component {


  constructor(props) {
    super(props);

    const apiToken = new ApiToken();
    if (!apiToken.isAuthenticated()) {
      console.log('Check Authentification Dashboard: FAIL');
      this.props.history.push(`/Login`)
    } else {
      console.log('Check Authentification Dashboard: PASS');
    }
  }

  render() {
    return (
      <div style={{background: '#ADB7BF'}}>

        <CardDeck style={{padding:'12px', height:'300px'}}>
          <Card>
            <CardHeader  className="text-center" style={{background: '#B9C5D5'}}>
              <h5 style={{color: 'black', fontFamily: 'Comic Sans MS'}}> Meal Combo</h5>
            </CardHeader>
            <CardBody>
            <MealCombo/>  
            </CardBody>
          </Card>

        </CardDeck>


        <CardDeck style={{padding:'12px', height:'450px'}}>
          <Card>
            <CardHeader  className="text-center" style={{background: '#B9C5D5'}}>
              <h5 style={{color: 'black', fontFamily: 'Comic Sans MS'}}> Available Meals</h5>
            </CardHeader>
            <CardBody>
              <AvailableMeals />
            </CardBody>
          </Card>

          <Card>
            <CardHeader  className="text-center" style={{background: '#B9C5D5'}}>
              <h5 style={{color: 'black', fontFamily: 'Comic Sans MS'}}> All Meals</h5>
            </CardHeader>
            <CardBody>
              <DisplayMeals />
            </CardBody>
          </Card>

        </CardDeck>


        <CardDeck style={{padding:'12px', height:'450px'}}>
          <Card>
            <CardHeader  className="text-center" style={{background: '#B9C5D5'}}>
              <h5 style={{color: 'black', fontFamily: 'Comic Sans MS'}}> Users </h5>
            </CardHeader>
            <CardBody>
              <DisplayUsers />
            </CardBody>
          </Card>

        </CardDeck>


        <CardDeck style={{padding:'12px', height:'275px'}}>
          <Card>
            <CardHeader  className="text-center" style={{background: '#B9C5D5'}}>
              <h5 style={{color: 'black', fontFamily: 'Comic Sans MS'}}> Header</h5>
            </CardHeader>
            <CardBody>
            </CardBody>
          </Card>


          <Card>
            <CardHeader  className="text-center" style={{background: '#B9C5D5'}}>
              <h5 style={{color: 'black', fontFamily: 'Comic Sans MS'}}>Header</h5>
            </CardHeader>
            <CardBody>
            </CardBody>
          </Card>

        </CardDeck>

      </div>
    );

  }
}


export default Dashboard;
