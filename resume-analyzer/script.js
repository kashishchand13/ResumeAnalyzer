function analyze(){

 let resume = document.getElementById("resume").value;
 let job = document.getElementById("job").value;

 fetch("http://localhost:8081/api/analyze",{

 method:"POST",

 headers:{
  "Content-Type":"application/json"
 },

 body: JSON.stringify({
   resumeText: resume,
   jobDescription: job
 })

 })

 .then(res=>res.json())

 .then(data=>{

 document.getElementById("result").innerHTML =
 "Match Score: "+data.score+
 "<br>Missing Skills: "+data.missingSkills;

 });

}