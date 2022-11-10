import Posts from './Posts/Posts';
import UserTable from './Users/UserTable';
import './App.css'

function App() {
  const loggedInAs = 'admin'
  return (
    <div className='flex-container'>
      <div className='flex-child'>
        <Posts currentUser={loggedInAs} />
      </div>
      <div className='flex-child'>
      <UserTable />
      </div>
    </div>
  );
}

export default App;
