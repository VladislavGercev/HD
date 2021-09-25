import React from 'react';
import {  Switch, Route } from 'react-router-dom'
import LoginContainer from './containers/LoginContainer';
import TicketsContainer from './containers/TicketsContainer';
import CreateTicketContainer from './containers/CreateTicketContainer';
import TicketContainer from './containers/TicketContainer';
import EditContainer from './containers/EditContainer'; 
import AttachmentContainer from './containers/AttachmentContainer';
import FeedbackContainer from './containers/FeedbackContainer';





function App() {
  return (
    <div>
        <Switch>
          <Route exact path='/login' component={LoginContainer} />
          <Route exact path='/tickets' component={TicketsContainer} />
          <Route exact path='/tickets/new' component={CreateTicketContainer} />
          <Route exact path='/tickets/:id' component={TicketContainer}/>
          <Route exact path='/tickets/:id/edit/' component={EditContainer} />
          <Route exact path='/tickets/:id/feedback/new' component={FeedbackContainer} />
          <Route exact path='/tickets/:id/feedback/' component={FeedbackContainer} />
          <Route exact path='/tickets/:ticketId/attachments/:attachmentId' component={AttachmentContainer} />
          
        </Switch>
  </div>
  )
}

export default App