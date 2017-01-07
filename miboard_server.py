from flask import Flask, request, session, render_template, jsonify;
from flaskext.mysql import MySQL;
import json, os, sys;
reload(sys)
sys.setdefaultencoding('utf-8')

UPLOAD_FOLDER = '/Users/JiyoungPark/Documents/Code/MidasIT/Photos'

app = Flask(__name__);
app.config['UPLOAD_FOLDER'] = UPLOAD_FOLDER
mysql = MySQL();

app.config['MYSQL_DATABASE_USER'] = 'root';
app.config['MYSQL_DATABASE_PASSWORD'] = 'akekdk1';
app.config['MYSQL_DATABASE_DB'] = 'miboard';

mysql.init_app(app);
conn = mysql.connect();
cursor = conn.cursor();

@app.route("/")
def helloworld():
	return "HelloWorld";

@app.route("/login", methods=['POST'])
def login():
	cursor.execute("SELECT password FROM miboard_member where id = \"" + request.form['id'] + "\"")
	result = ""
	columns = tuple( d[0] for d in cursor.description)
	for row in cursor:
		result = row

	if request.form['password'] == result[0]:
		return jsonify({'result_code':'200'}), 200
	if equest.form['password'] != result[0]:
		return jsonify({'result_code':'405'}), 200
	else:
		return jsonify({'result_code':'404'}), 200

@app.route("/save", methods=['GET','POST'])
def write():
	if request.method == 'POST':

		file = request.files['file']
		file.save(os.path.join(app.config['UPLOAD_FOLDER'], request.form['imageName'] + ".jpg"))

		print "id:", request.form['id']
		print "title:", request.form['title']
		print "content:", request.form['content']
		print "imageName:", request.form['imageName']
	 	content = request.form['content'].encode('utf-8')

		sql = """INSERT INTO miboard_board(id, title, content, imageName) VALUES (%s, %s, %s, %s)"""
		#sql = "INSERT INTO miboard_board(id, title, content, imageName) VALUES (\'" + request.form['id'] + "\', \'" + request.form['title'].encode("utf-8") + "\', \'" + request.form['content'].encode("utf-8") + "\', \'" + request.form['imageName'] + "\');"
		#cursor.execute(sql)
		#print sql
		cursor.execute(sql, (request.form['id'], request.form['title'].encode("utf-8"), request.form['content'].encode("utf-8"), request.form['imageName']))
	
		conn.commit()
	

@app.route("/test", methods=['GET'])
def test():
	cursor.execute("SELECT password FROM miboard_member where id = \"" + "b\"")
	result = ""
	columns = tuple( d[0] for d in cursor.description)

	for row in cursor:
		result = row

	return json.dumps(result)

@app.route("/login/member", methods=['GET', 'POST'])
def members():
	
	cursor.execute("SELECT * FROM miboard_member")

	result = []
	columns = tuple( d[0] for d in cursor.description)

	for row in cursor:
		result.append(dict(zip(columns, row)))

	return json.dumps(result);

if __name__ == '__main__':
	app.run(debug=True, host="0.0.0.0", port=5009);