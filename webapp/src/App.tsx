import React, {useEffect, useState} from 'react';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import {
  BrowserRouter as Router,
  Redirect,
  Route,
  Switch,
  useHistory,
  Link
} from 'react-router-dom'
import Login from "./Login";
import Registration from "./Registration";
import Home from "./Home";
import {Tooltip} from "@material-ui/core";
import redirectToHomeIfTokenValid from "./utils/redirectToHome";
import redirectToLoginIfTokenInvalid from "./utils/redirectToLogin";


function App() {
  const history = useHistory()
  const { menuButton, title, topBar } = useStyles();
  const [isSignedIn, setIsSignedIn] = useState(false)
  const [signedInDoge, setSignedInDoge] = useState<Doge>()
  const redirect = () => {
    redirectToHomeIfTokenValid(history)
    redirectToLoginIfTokenInvalid(history)
    getDogeInfo()
  }

  useEffect(getDogeInfo, [])

  function getDogeInfo() {
    fetch("/api/v1/doge", {
      headers: { Authorization: localStorage.getItem('token') || '' }
    }).then(res => {
      if (!res.ok) setSignedInDoge(undefined)
      res.json().then((doge: Doge) => {
        setSignedInDoge(doge)
      })
    })
  }

  const { firstname, lastname } = signedInDoge || {}

  return (
    <div>
      <div className={topBar}>
        <AppBar position="fixed">
          <Toolbar>
            <IconButton edge="start" className={menuButton} color="inherit" aria-label="menu">
              <MenuIcon />
            </IconButton>
            <Typography variant="h6" className={title}>
              <Link component={React.forwardRef((props, ref) => (
                <a href style={{ textDecoration: 'none', color: 'inherit' }} {...props} >{props.children}</a>
              ))} to='/home'>
                DOGEBOOK
              </Link>
            </Typography>
            {signedInDoge ?
              <>
                <Tooltip title='Click here to logout'>
                  <Button
                    color="inherit"
                    onClick={() => {
                      localStorage.removeItem("token")
                      getDogeInfo()
                      redirect()
                    }}
                  >
                    {firstname} {lastname}
                  </Button>
                </Tooltip>
              </> :
              <>
                <Button
                  color="inherit"
                  onClick={() => history.push("/login")}
                >
                  Sign in
                </Button>
                <Button
                  color="inherit"
                  onClick={() => history.push("/registration")}
                >
                  Sign up
                </Button>
              </>
            }
          </Toolbar>
        </AppBar>
      </div>
      <div style={{ marginTop: '6rem' }}>
        <Route exact path='/login'><Login onLogin={getDogeInfo} /></Route>
        <Route exact path='/registration'><Registration /></Route>
        <Route exact path='/home'>{signedInDoge ? <Home doge={signedInDoge} /> : null}</Route>
        <Route exact path='/'><Redirect to='/home' /></Route>
      </div>
    </div>
  );
}

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    topBar: {
      flexGrow: 1,
    },
    menuButton: {
      marginRight: theme.spacing(2),
    },
    title: {
      flexGrow: 1,
    },
  }),
);

export default App;
