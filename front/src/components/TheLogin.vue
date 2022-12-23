<template>
    <section class="login row">
		<div class="login_box col-12 row">
			<div class="left col-6">
				<div class="top_link"><a href="#"><img src="https://drive.google.com/u/0/uc?id=16U__U5dJdaTfNGobB_OpwAJ73vM50rPV&export=download" alt="">Return home</a></div>
    <form  @submit.prevent="login" action="">
        <input type="text" placeholder="email" v-model="Email"/>
        <br>
        <input type="password" placeholder="Password" v-model="Password"/>
        <button type="submit" value="login">login</button>
    </form>
    </div>
    </div>
    </section>
</template>

<script>
export default {
    data(){
        return{
          
          Email:"",
          loggedPersonMail:"",
          successful:false,
        }
    },
    methods:{
        async login(){ 
            this.clicked=true;
           await fetch(
                "https://localhost:44313/api/login/user",
                {
                    method: "POST",
                    headers: {
                    "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                    Email: this.Email,
                   
                    
                    }),
                
                }
                )
                // .then((response) => {
                //     if (!response.ok) {
                //     throw new Error("Could not save the data");
                //     }else{
                //         console.log(response)
                //         this.Email="";
                       
                //     }
                // }).then((data)=>{
                //     console.log("-----")
                //     console.log(data);
                // })
        //         .then((response) => {
        //   if (response.ok) {
        //     this.x= response.json();
        //     console.log("--x--")
        //     console.log(this.x)
        //     //console.log(response)
        //   }
        // })

                          
               // Converting to JSON
            //.then(response => response.json())
            //.then(res)
            
            // Displaying results to console
          
           // .then(json => console.log(json))
            .then((response)=>{
                if(response.ok){
                    this.loggedPersonMail=  this.Email;

                    this.$store.dispatch('user/addLoggedUser',{
                    Email:this.loggedPersonMail,
                    });

                    this.$router.replace('/user')
                }
            })
               

                .catch((error) => {
                    this.error = error;
                    console.log(error);
                }
            );

           // console.log(this.loggedPersonMail)
           

    
                
        }
    }
    
   
}
</script>


<style scoped>

.login {
    height: 100vh;
    width: 100%;
    background: radial-gradient(#653d84, #332042);
    position: relative;
}
.login_box {
    width: 1050px;
    height: 600px;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    background: #fff;
    border-radius: 10px;
    box-shadow: 1px 4px 22px -8px #0004;
    display: flex;
    overflow: hidden;
}
.login_box .left{
  width: 41%;
  height: 100%;
  padding: 25px 25px;
  
}
.login_box .right{
  width: 59%;
  height: 100%  
}
.left .top_link a {
    color: #452A5A;
    font-weight: 400;
}
.left .top_link{
  height: 20px
}
.left .contact{
	display: flex;
    align-items: center;
    justify-content: center;
    align-self: center;
    height: 100%;
    width: 73%;
    margin: auto;
}
.left h3{
  text-align: center;
  margin-bottom: 40px;
}
.left input {
    border: none;
    width: 80%;
    margin: 15px 0px;
    border-bottom: 1px solid #4f30677d;
    padding: 7px 9px;
    width: 100%;
    overflow: hidden;
    background: transparent;
    font-weight: 600;
    font-size: 14px;
}
.left{
	background: linear-gradient(-45deg, #dcd7e0, #fff);
}
.submit {
    border: none;
    padding: 15px 70px;
    border-radius: 8px;
    display: block;
    margin: auto;
    margin-top: 120px;
    background: #583672;
    color: #fff;
    font-weight: bold;
    -webkit-box-shadow: 0px 9px 15px -11px rgba(88,54,114,1);
    -moz-box-shadow: 0px 9px 15px -11px rgba(88,54,114,1);
    box-shadow: 0px 9px 15px -11px rgba(88,54,114,1);
}



.right {
	background: linear-gradient(rgba(242, 57, 127, 0.7) , rgba(175, 70, 189, 0.71) 100%);
	color: #fff;
	position: relative;
}

.right .right-text{
  height: 100%;
  position: relative;
  transform: translate(0%, 45%);
}
.right-text h2{
  display: block;
  width: 100%;
  text-align: center;
  font-size: 50px;
  font-weight: 500;
}
.right-text h5{
  display: block;
  width: 100%;
  text-align: center;
  font-size: 19px;
  font-weight: 400;
}

.right .right-inductor{
  position: absolute;
  width: 70px;
  height: 7px;
  background: #fff0;
  left: 50%;
  bottom: 70px;
  transform: translate(-50%, 0%);
}
.top_link img {
    width: 28px;
    padding-right: 7px;
    margin-top: -3px;
}

@media only screen and (max-width: 480px) {
    html {
    font-size: 50.5%;
  }
  .login_box {
    width: 480px;
    /* height: 600px; */
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    background: #fff;
    border-radius: 10px;
    box-shadow: 1px 4px 22px -8px #0004;
    display: flex;
    overflow: hidden;
}

.login_box .left{
  width: 11%;
  height: 100%;
  padding: 25px 25px;
  
}
}




</style>