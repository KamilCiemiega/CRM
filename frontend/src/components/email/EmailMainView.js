import { useState } from "react";
import Navigation from "./navigation/Navigation";
import EmailCreator from "./emailCreator/EmailCreator";

const EmailMainView = (props) => {
    return(
        <div>
            <Navigation />
            <EmailCreator />
        </div>
    )

}

export default EmailMainView;